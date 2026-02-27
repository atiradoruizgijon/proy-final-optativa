package com.alejandro.kook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KookApplication {

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(3000);
		SpringApplication.run(KookApplication.class, args);
	}

}
