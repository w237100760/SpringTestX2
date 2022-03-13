package com.springboot.springtestx2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@MapperScan(basePackages = "com.springboot.springtestx2.DB_Mysql.*",
annotationClass = Repository.class)
@SpringBootApplication(scanBasePackages = {"com.springboot.springtestx2.RabbitMq.*"})
public class SpringTestX2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringTestX2Application.class, args);
	}
}
