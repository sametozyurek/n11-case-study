package com.n11.case_study;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "N11 CASE STUDY API"))
public class CaseStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseStudyApplication.class, args);
	}

}
