package com.SiGroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SiGroupJeAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiGroupJeAutomationApplication.class, args);
	}

}
