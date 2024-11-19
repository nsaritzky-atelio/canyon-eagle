package com.atelio.fintel.canyoneagle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atelio.fintel.canyoneagle", "org.atelio.demo"})
public class CanyonEagleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanyonEagleApplication.class, args);
	}

}
