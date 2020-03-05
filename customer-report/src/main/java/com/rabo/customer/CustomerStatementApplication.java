package com.rabo.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CustomerStatementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerStatementApplication.class, args);
	}

}
