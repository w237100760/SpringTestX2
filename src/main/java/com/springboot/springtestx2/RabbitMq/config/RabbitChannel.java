package com.springboot.springtestx2.RabbitMq.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitChannel {
    @Value("${rabbitMq.host}")
    private String host;
    @Value("${rabbitMq.username}")
    private String username;
    @Value("${rabbitMq.password}")
    private String password;
    @Value("${rabbitMq.port}")
    private int port;
    @Value("${rabbitMq.vHost}")
    private String vHost;
    /*
     * autoAck default: false 默认需要收到消费者应答
     * autoAck = true时 不管消费者是否收到消息
     * */
    private static final boolean durable = true;
    private static final String QUEUE_NAME = "canal.test";
    private static final String EXCHANGE_NAME = "canal.exchange";
    private static final String ROUTING_KEY = "canal.exchange.routingKey";
    /*
    * 1. direct: a message goes to the queues whose binding key exactly matches the routing key of the message(不同的queue可以设置相同的routeKey)
    * 2. topic
    * 3. headers
    * 4. fanout: broadcasts all messages to all consumers
    * */
    private static final String EXCHANGE_TYPE = "direct";

    @Bean
    public Channel consumerChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = getConnectionFactory();
        //consumer 应该保持Connection和Channel资源不释放
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        channel.basicQos(1);// accept only one unack-ed message at a time
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        return channel;
    }

    private ConnectionFactory getConnectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setVirtualHost(vHost);
        return factory;
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
 * - queue durable-- channel.queueDeclare() (producer and consumer code 都配置)
 * - messages -- channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
 * */