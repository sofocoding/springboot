package com.gcit.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan("com.gcit.lms.service")
@ComponentScan("com.gcit.lms.dao")
@SpringBootApplication
public class SpringadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringadminApplication.class, args);
	}
}
