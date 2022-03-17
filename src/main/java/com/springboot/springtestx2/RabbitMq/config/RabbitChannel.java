package com.springboot.springtestx2.RabbitMq.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@DependsOn("rabbitConfig")
@Configuration
public class RabbitChannel {
    private final RabbitConfig rabbitConfig;
    @Autowired
    public RabbitChannel(RabbitConfig rabbitConfig) {
        this.rabbitConfig = rabbitConfig;
    }

    @DependsOn("connectionFactory")
    @Bean
    public Channel consumerChannel(@Autowired ConnectionFactory factory) throws IOException, TimeoutException {
        //consumer 应该保持Connection和Channel资源不释放
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(rabbitConfig.QUEUE_NAME, RabbitConfig.durable, false, false, null);
        channel.basicQos(RabbitConfig.prefetch);
        channel.exchangeDeclare(rabbitConfig.EXCHANGE_NAME, RabbitConfig.EXCHANGE_TYPE);
        channel.queueBind(rabbitConfig.QUEUE_NAME, rabbitConfig.EXCHANGE_NAME, rabbitConfig.ROUTING_KEY);
        return channel;
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitConfig.HOST);
        factory.setUsername(rabbitConfig.USER_NAME);
        factory.setPassword(rabbitConfig.PASSWORD);
        factory.setPort(rabbitConfig.PORT);
        factory.setVirtualHost(rabbitConfig.V_HOST);
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