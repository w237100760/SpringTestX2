package com.springboot.springtestx2.RabbitMq.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
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
    @Value("${rabbitMq.queue}")
    public String QUEUE_NAME;
    @Value("${rabbitMq.exchange}")
    public String EXCHANGE_NAME;
    @Value("${rabbitMq.route}")
    public String ROUTING_KEY;

    public static final boolean durable = true;//queue durable
    /*
    * 1. direct: a message goes to the queues whose binding key exactly matches the routing key of the message(不同的queue可以设置相同的routeKey)
    * 2. topic: https://www.rabbitmq.com/tutorials/tutorial-five-java.html
    * 3. headers
    * 4. fanout: broadcasts all messages to all consumers
    * */
    public static final String EXCHANGE_TYPE = "direct";
    public static final int prefetch = 1;//consumer accept only one unAck-ed message at a time

    @DependsOn("connectionFactory")
    @Bean
    public Channel consumerChannel(@Autowired ConnectionFactory factory) throws IOException, TimeoutException {
        //consumer 应该保持Connection和Channel资源不释放
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        channel.basicQos(prefetch);
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        return channel;
    }

    @Bean
    public ConnectionFactory connectionFactory(){
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
 * - queue durable-- channel.queueDeclare()
 * - messages -- channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
 * */