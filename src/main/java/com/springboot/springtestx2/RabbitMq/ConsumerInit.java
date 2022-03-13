package com.springboot.springtestx2.RabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConsumerInit implements InitializingBean {
    /*
     * autoAck default: false 默认需要收到消费者应答
     * autoAck = true时 不管消费者是否收到消息
     * */
    private static final boolean autoAck = false;
    private static final boolean durable = true;
    private static final String QUEUE_NAME = "hello";

    @Value("$(rabbitMq.host)")
    private String host;
    @Value("$(rabbitMq.username)")
    private String username;
    @Value("$(rabbitMq.password)")
    private String password;
    @Value("$(rabbitMq.port)")
    private int port;
    @Value("$(rabbitMq.vHost)")
    private String vHost;

    private ConnectionFactory factory;
    public void connectionFactory() {
        if (this.factory != null){
            return;
        }
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setVirtualHost(vHost);
        this.factory = factory;
    }

    /*Initial consumer*/
    @Override
    public void afterPropertiesSet() throws Exception {
        connectionFactory();
        //consumer 应该保持Connection和Channel资源不释放
        Connection connection = this.factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        channel.basicQos(1);// accept only one unack-ed message at a time

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

            try {
                //doWork
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }
}

/*
 * dispatch
 * default -- round-robin: 多个消费者 will get the same number of messages
 * Fair dispatch: channel.basicQos(prefetchCount); 如果其中一个consumer在round-robin下太忙，it will dispatch it to the next worker that is not still busy.
 *
 * Durability
 * 1. if the consumer dies, the task isn't lost -- Message acknowledgment (channel.basicAck())
 * 2. if RabbitMQ server stops. -- mark both the queue and messages as durable
 * - queue -- channel.queueDeclare() (producer and consumer code 都配置)
 * - messages -- channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
 *
 *
 * */