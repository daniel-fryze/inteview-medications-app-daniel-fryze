package pl.education.fryzedaniel.restapp;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import pl.education.fryzedaniel.restapp.api.handlers.SimpleCorsFilter;
import pl.education.fryzedaniel.restapp.api.utilities.Constants;

/**
 * The component containing the configuration (default to all profiles) for the Spring application.
 * 
 * @author daniel.fryze
 */
@Configuration
public class ConfigurationDefault {

	/**
	 * Defines the component responsible for configuring Servlet filters for the app.
	 * It is used generally only to add the CORS filter to the application.
	 * 
	 * @return the component 'FilterRegistrationBean'
	 */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SimpleCorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("corsFilter");
        return registration;
    }

    /**
     * Defines the component responsible for providing access to application properties.
     * 
     * @return the resource messages component
     */
	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:" + Constants.MESSAGES_PATH);
		messageBundle.setDefaultEncoding(Constants.DEFAULT_ENCODING);
		return messageBundle;
	}

}