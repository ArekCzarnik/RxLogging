package com.infinity;

import com.infinity.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopApplication implements CommandLineRunner {

	// Simple example shows how an application can spy on itself with AOP

	@Autowired
	private HelloWorldService helloWorldService;

	@Override
	public void run(String... args) {
		System.out.println(helloWorldService.text().toBlocking().first());
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AopApplication.class, args);
	}
}
