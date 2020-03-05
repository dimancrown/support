package com.rabo.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author diman
 * Application starter
 */
@SpringBootApplication
@EnableSwagger2
public class CustomerStatementApplication {
	/**
	 * Application startup arguments
	 * @param args
	 */

	public static void main(String[] args) {
		SpringApplication.run(CustomerStatementApplication.class, args);
	}

}
