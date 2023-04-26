package com.ti.demo;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	@PostConstruct
	public void setup(){
		Stripe.apiKey = "sk_test_tR3PYbcVNZZ796tH88S4VQ2u";
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
