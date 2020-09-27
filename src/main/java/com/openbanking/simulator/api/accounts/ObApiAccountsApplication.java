package com.openbanking.simulator.api.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication
public class ObApiAccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObApiAccountsApplication.class, args);
	}

}
