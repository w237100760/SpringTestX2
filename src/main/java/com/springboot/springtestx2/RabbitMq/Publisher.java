package com.springboot.springtestx2.RabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.springboot.springtestx2.RabbitMq.config.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Publisher {
    public static Channel channel;

    private final RabbitConfig rabbitConfig;
    @Autowired
    public Publisher(RabbitConfig rabbitConfig, ConnectionFactory factory) throws IOException, TimeoutException {
        this.rabbitConfig = rabbitConfig;
        channel = factory.newConnection().createChannel();
    }

    public void publish(String msg) {
        try {
            channel.exchangeDeclare(rabbitConfig.EXCHANGE_NAME,RabbitConfig.EXCHANGE_TYPE);
            /*Handling Publisher Confirms Asynchronously*/
            channel.confirmSelect();//This method must be called on every channel that you expect to use publisher confirms
            ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();
            //消息确认成功 回调函数
            ConfirmCallback ackCallback = (sequenceNumber, multiple) -> {
                if (multiple) {
                    ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(
                            sequenceNumber, true);
                    confirmed.clear();
                } else {
                    outstandingConfirms.remove(sequenceNumber);
                }
                System.out.println("确认的消息："+sequenceNumber);
            };
            //消息确认失败 回调函数
            ConfirmCallback nAckCallback = (sequenceNumber,multiple)->{
                String body = outstandingConfirms.get(sequenceNumber);
                System.err.format("Message with body %s has been nack-ed. Sequence number: %d, multiple: %b%n",
                        body, sequenceNumber, multiple);
//                ackCallback.handle(sequenceNumber, multiple);
            };
            channel.addConfirmListener(ackCallback,nAckCallback);

            outstandingConfirms.put(channel.getNextPublishSeqNo(), msg);
            channel.basicPublish(rabbitConfig.EXCHANGE_NAME, rabbitConfig.ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    msg.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
