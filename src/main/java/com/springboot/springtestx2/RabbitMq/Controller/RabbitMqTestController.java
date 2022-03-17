package com.springboot.springtestx2.RabbitMq.Controller;

import com.springboot.springtestx2.RabbitMq.Publisher;
import com.springboot.springtestx2.Utils.SpringBeanUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/RabbitMq")
@RestController
public class RabbitMqTestController {

    @GetMapping("/test")
    public void publish(String msg) {
        Publisher publisher = SpringBeanUtil.getBean("publisher");
        publisher.publish(msg+"---"+publisher.hashCode());
    }
}
