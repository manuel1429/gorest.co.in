package com.jmrt.gorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
	
	@Bean
	public RestTemplate getRestTemplate() {
	     return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
