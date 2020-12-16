package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.service.MessagingService;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication(scanBasePackages = "com.example.demo")
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private MessagingService messagingService;

//	@Autowired
//	private ConfigurableApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final String topic = "gps/data";

		messagingService.subscribe(topic);

//		context.close();
	}

}
