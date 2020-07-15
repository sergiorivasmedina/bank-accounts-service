package com.bootcamp.bankaccounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@SpringBootApplication
@EnableSwagger2WebFlux
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Reactive Documentation - Project 1")
                .description("Reactive API Documentation - Sergio Rivas Medina")
                .version("1.0.0")
                .build();
        }
        @Bean
        public Docket docket() {
                return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(this.apiInfo())
                        .enable(true)
                        .select()
                        .paths(PathSelectors.any())
                        .build();
        }

}
