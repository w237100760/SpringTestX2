package com.springboot.springtestx2.RabbitMq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {
    @Value("${rabbitMq.host}")
    public String HOST;
    @Value("${rabbitMq.username}")
    public String USER_NAME;
    @Value("${rabbitMq.password}")
    public String PASSWORD;
    @Value("${rabbitMq.port}")
    public int PORT;
    @Value("${rabbitMq.vHost}")
    public String V_HOST;
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

    /*
     * autoAck default: false 默认需要收到消费者应答
     * autoAck = true时 不管消费者是否收到消息
     * Automatic acknowledgement mode is therefore only recommended for consumers that can process deliveries efficiently and at a steady rate.
     * */
    public static final boolean autoAck = false;
    public static final boolean multipleAck = true;//true: Acknowledging Multiple Deliveries at Once, 确认这个tag之前的多条消息
    public static final boolean reQueue = false;//true 重新进入队列; false 丢弃或者加入死信队列
}
