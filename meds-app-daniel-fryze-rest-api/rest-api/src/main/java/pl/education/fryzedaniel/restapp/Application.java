package pl.education.fryzedaniel.restapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The main class for the Spring Boot application - used to start the app.
 * 
 * @author daniel.fryze
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	/**
	 * Main method for starting the application.
	 * @param args application start arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}