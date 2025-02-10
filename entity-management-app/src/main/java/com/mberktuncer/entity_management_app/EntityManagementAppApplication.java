package com.mberktuncer.entity_management_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EntityManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntityManagementAppApplication.class, args);
	}

}
