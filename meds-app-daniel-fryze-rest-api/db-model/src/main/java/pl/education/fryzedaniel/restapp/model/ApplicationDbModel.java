package pl.education.fryzedaniel.restapp.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ApplicationDbModel {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationDbModel.class, args);
	}
}