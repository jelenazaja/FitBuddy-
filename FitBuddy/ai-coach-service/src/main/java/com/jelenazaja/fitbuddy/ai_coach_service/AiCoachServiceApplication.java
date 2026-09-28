package com.jelenazaja.fitbuddy.ai_coach_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AiCoachServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiCoachServiceApplication.class, args);
	}

}
