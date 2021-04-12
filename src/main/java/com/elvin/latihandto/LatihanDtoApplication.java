package com.elvin.latihandto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LatihanDtoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LatihanDtoApplication.class, args);
	}

}
