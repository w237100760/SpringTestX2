package com.springboot.springtestx2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.springboot.springtestx2.Aop.*"})
public class SpringTestX2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringTestX2Application.class, args);
	}
}
