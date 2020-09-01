package uk.gov.gsi.dwp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

@SpringBootApplication

public class DwpUserFinderApplication {

	@Value("${bpdts.api.connection.timeout}")
	private int timeOut;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(Duration.ofMillis(timeOut)).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(DwpUserFinderApplication.class, args);
	}



}
