package pl.education.fryzedaniel.restapp;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("integration-tests")
@Configuration
@ComponentScan
@EnableAutoConfiguration
//@EnableCaching
public class ApplicationIntegrationTests {
}