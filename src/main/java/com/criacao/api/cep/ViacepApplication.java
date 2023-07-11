package com.criacao.api.cep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ViacepApplication {

	private static ConfigurableApplicationContext ctx;
	public static void main(String[] args) {
		ctx = SpringApplication.run(ViacepApplication.class, args);
	}

	public static void close(int code){
		SpringApplication.exit(ctx, () -> code);
	}
}


