package com.springboot.springtestx2.RabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.springboot.springtestx2.RabbitMq.config.RabbitChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

@Component
public class Publisher {
    private final RabbitChannel rabbitChannel;
    private final ConnectionFactory factory;
    @Autowired
    public Publisher(RabbitChannel rabbitChannel, ConnectionFactory factory) {
        this.rabbitChannel = rabbitChannel;
        this.factory = factory;
    }

    public void publish(String msg){
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel())
        {
            channel.exchangeDeclare(rabbitChannel.EXCHANGE_NAME,RabbitChannel.EXCHANGE_TYPE);
            /*Handling Publisher Confirms Asynchronously*/
            channel.confirmSelect();//This method must be called on every channel that you expect to use publisher confirms
            ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();
            ConfirmCallback cleanOutstandingConfirms = (sequenceNumber, multiple) -> {
                if (multiple) {
                    ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(
                            sequenceNumber, true
                    );
                    confirmed.clear();
                } else {
                    outstandingConfirms.remove(sequenceNumber);
                }
            };

            channel.addConfirmListener(cleanOutstandingConfirms, (sequenceNumber, multiple) -> {
                String body = outstandingConfirms.get(sequenceNumber);
                System.err.format(
                        "Message with body %s has been nack-ed. Sequence number: %d, multiple: %b%n",
                        body, sequenceNumber, multiple
                );
                cleanOutstandingConfirms.handle(sequenceNumber, multiple);
            });
            outstandingConfirms.put(channel.getNextPublishSeqNo(), msg);
            channel.basicPublish(rabbitChannel.EXCHANGE_NAME, rabbitChannel.ROUTING_KEY, null, msg.getBytes("UTF-8"));
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
