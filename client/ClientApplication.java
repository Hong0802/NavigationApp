package com.example.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.client.service.MessagingService;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "com.example.client")
public class ClientApplication implements CommandLineRunner {


	@Autowired
	private MessagingService messagingService;

	@Autowired
	private ConfigurableApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final String topic = "gps/data";

		Scanner scanner = new Scanner(new File("src/main/resources/gps-data.txt"));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.contains("x")) {
				// process the line
				messagingService.publish(topic, line, 0, true);
				System.out.println(line);
				TimeUnit.SECONDS.sleep(1);
			}
		}

		context.close();
	}
}
