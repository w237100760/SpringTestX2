package com.springboot.springtestx2.RabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ConsumerInit implements InitializingBean {
    /*
     * autoAck default: false 默认需要收到消费者应答
     * autoAck = true时 不管消费者是否收到消息
     * */
    private static final boolean autoAck = false;

    private final Channel consumerChannel;
    @Autowired
    public ConsumerInit(@Qualifier("consumerChannel") Channel consumerChannel) {
        this.consumerChannel = consumerChannel;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

            try {
                //doWork
            } finally {
                System.out.println(" [x] Done");
                consumerChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        consumerChannel.basicConsume("canal.test", autoAck, deliverCallback, consumerTag -> { });
    }
}