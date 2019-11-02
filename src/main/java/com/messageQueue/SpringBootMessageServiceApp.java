package com.messageQueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMessageServiceApp {

	
	public static void main(String[] args) {
		SpringApplication.run(
				new Object[] { SpringBootMessageServiceApp.class }, args);
	}
}