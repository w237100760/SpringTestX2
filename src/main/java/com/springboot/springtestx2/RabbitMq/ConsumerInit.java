package com.springboot.springtestx2.RabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.springboot.springtestx2.RabbitMq.config.RabbitChannel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerInit implements InitializingBean {
    /*
     * autoAck default: false 默认需要收到消费者应答
     * autoAck = true时 不管消费者是否收到消息
     * Automatic acknowledgement mode is therefore only recommended for consumers that can process deliveries efficiently and at a steady rate.
     * */
    private static final boolean autoAck = false;
    private static final boolean multipleAck = true;//true: Acknowledging Multiple Deliveries at Once, 确认这个tag之前的多条消息
    private static final boolean reQueue = false;//true 重新进入队列; false 丢弃或者加入死信队列

    private final RabbitChannel rabbitChannel;
    private final Channel consumerChannel;
    @Autowired
    public ConsumerInit(RabbitChannel rabbitChannel, Channel consumerChannel) {
        this.rabbitChannel = rabbitChannel;
        this.consumerChannel = consumerChannel;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" ["+consumerTag+"] Received '" + message + "'");

            try {
                //doWork
            } catch (Exception e){
                e.printStackTrace();
                consumerChannel.basicReject(delivery.getEnvelope().getDeliveryTag(), reQueue);
                //TODO 加入死信队列
            }
            finally {
                System.out.println(" ["+consumerTag+"] Done");
                consumerChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), multipleAck);
            }
        };
        consumerChannel.basicConsume(rabbitChannel.QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
        /*
        * 1. push--> basicConsume
        * 2. pull--> basicGet
        * */
    }
}