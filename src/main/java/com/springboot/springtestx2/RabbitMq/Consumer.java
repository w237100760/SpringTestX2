package com.springboot.springtestx2.RabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.springboot.springtestx2.RabbitMq.config.RabbitChannel;
import com.springboot.springtestx2.RabbitMq.config.RabbitConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer implements InitializingBean {
    private final RabbitConfig rabbitConfig;
    private final Channel consumerChannel;
    @Autowired
    public Consumer(RabbitConfig rabbitConfig, Channel consumerChannel) {
        this.rabbitConfig = rabbitConfig;
        this.consumerChannel = consumerChannel;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" ["+consumerTag+"] Received '" + message + "'");

            try {
                //doWork

                System.out.println(" ["+consumerTag+"] Done");
                consumerChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), RabbitConfig.multipleAck);
            } catch (Exception e){
                e.printStackTrace();
                consumerChannel.basicReject(delivery.getEnvelope().getDeliveryTag(), RabbitConfig.reQueue);
                //TODO 加入死信队列
            }
        };
        consumerChannel.basicConsume(rabbitConfig.QUEUE_NAME, RabbitConfig.autoAck, deliverCallback, consumerTag -> { });
    }
}