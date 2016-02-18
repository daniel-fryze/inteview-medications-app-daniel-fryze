package pl.education.fryzedaniel.restapp;

import javax.servlet.Filter;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;

import pl.education.fryzedaniel.restapp.api.handlers.SimpleCorsFilter;
import pl.education.fryzedaniel.restapp.model.repositories.MedicationRepository;

@Configuration
@ComponentScan
@EnableAutoConfiguration
//@EnableCaching
public class Application {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(getCorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("corsFilter");
        return registration;
    }

    @Bean(name = "corsFilter")
    public Filter getCorsFilter() {
        return new SimpleCorsFilter();
    }

	@Bean (name = "appProperties")
	public PropertiesFactoryBean getPropertiesFactoryBean() {
		PropertiesFactoryBean propertiesFactory = new PropertiesFactoryBean();
		propertiesFactory.setLocation(new ClassPathResource("app.properties"));
		return propertiesFactory;
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:messages/messages");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
	    DispatcherServlet servlet = new DispatcherServlet();
	    servlet.setDispatchOptionsRequest(true);
	    return servlet;
	}

	@Bean
	CommandLineRunner init(MedicationRepository medicationRepository) {

		return new CommandLineRunner() {

			@Override
			public void run(String... arg0) throws Exception {

//				 medicationRepository.save(new Medication(
//				 new Date(),
//				 "Boniva",
//				 "Ibandronate",
//				 "A bisphosphonate which inhibits bone resorption via actions
//				 on osteoclasts or on osteoclast precursors; decreases the
//				 rate of bone resorption, leading to an indirect increase in
//				 bone mineral density.",
//				 "Oral: 150 mg once monthly",
//				 "Calcium Salts: May decrease the serum concentration of
//				 Bisphosphonate Derivatives. Management: Avoid administration
//				 of oral calcium supplements within: 2 hours before or after
//				 tiludronate/clodronate/etidronate; 60 minutes after oral
//				 ibandronate; or 30 minutes after alendronate/risedronate.",
//				 "Gilead Sciences"));
//				 medicationRepository.save(new Medication(
//				 new Date(),
//				 "Videx",
//				 "Ibandronate",
//				 "A bisphosphonate which inhibits bone resorption via actions
//				 on osteoclasts or on osteoclast precursors; decreases the
//				 rate of bone resorption, leading to an indirect increase in
//				 bone mineral density.",
//				 "Oral: 150 mg once monthly",
//				 "Calcium Salts: May decrease the serum concentration of
//				 Bisphosphonate Derivatives. Management: Avoid administration
//				 of oral calcium supplements within: 2 hours before or after
//				 tiludronate/clodronate/etidronate; 60 minutes after oral
//				 ibandronate; or 30 minutes after alendronate/risedronate.",
//				 "Gilead Sciences"));
//				 medicationRepository.save(new Medication(
//				 new Date(),
//				 "Trizivir",
//				 "Ibandronate",
//				 "A bisphosphonate which inhibits bone resorption via actions
//				 on osteoclasts or on osteoclast precursors; decreases the
//				 rate of bone resorption, leading to an indirect increase in
//				 bone mineral density.",
//				 "Oral: 150 mg once monthly",
//				 "Calcium Salts: May decrease the serum concentration of
//				 Bisphosphonate Derivatives. Management: Avoid administration
//				 of oral calcium supplements within: 2 hours before or after
//				 tiludronate/clodronate/etidronate; 60 minutes after oral
//				 ibandronate; or 30 minutes after alendronate/risedronate.",
//				 "Gilead Sciences"));
//				 medicationRepository.save(new Medication(
//				 new Date(),
//				 "Truvada",
//				 "Ibandronate",
//				 "A bisphosphonate which inhibits bone resorption via actions",
//				 "Oral: 150 mg once monthly",
//				 "Calcium Salts: May decrease the serum concentration of",
//				 "Gilead Sciences"));

				// RestTemplate rest = new RestTemplate();
				// ResponseEntity<Object[]> entity =
				// rest.getForEntity("http://localhost:9200/medications/daniel",
				// Object[].class);
			}

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}