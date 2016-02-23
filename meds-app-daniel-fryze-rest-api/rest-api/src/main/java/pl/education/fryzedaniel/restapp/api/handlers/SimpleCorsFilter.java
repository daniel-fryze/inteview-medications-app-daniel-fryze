package pl.education.fryzedaniel.restapp.api.handlers;

import static pl.education.fryzedaniel.restapp.api.utilities.Constants.*;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * MVC filter of class <code>javax.servlet.Filter</code> responsible for fully enabling CORS
 * support for rest application. It's appliance is necessary because of the problem with handling
 * OPTIONS requests (sent internally by AngularJS) from different origins. It's mostly technical
 * configuration specific to AngularJS clients.
 * 
 * @author daniel.fryzek
 */
@Component
public class SimpleCorsFilter implements Filter {

	/** {@inheritDoc} */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Logger.getLogger(this.getClass()).debug("Servlet CORS initialization.");
	}

	/** {@inheritDoc} */
	@Override
	public void doFilter(ServletRequest request, ServletResponse resp, 
			FilterChain chain) throws IOException, ServletException {

		((HttpServletResponse) resp).setHeader(HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		((HttpServletResponse) resp).setHeader(HEADER_ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS");
		((HttpServletResponse) resp).setHeader(HEADER_ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, x-requested-with");

		chain.doFilter(request, resp);
	}

	/** {@inheritDoc}} */
	@Override
	public void destroy() {
		Logger.getLogger(this.getClass()).debug("Servlet CORS destruction.");
	}

}