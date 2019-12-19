package kmg.sbr.backend.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.jboss.logging.Logger;
import org.springframework.web.filter.GenericFilterBean;

import kmg.sbr.backend.handler.RestAuthenticationEntryPoint;

public class CustomBasicFilter extends GenericFilterBean {
	private static Logger logger = Logger.getLogger(CustomBasicFilter.class);
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.infof("request from ip : {}", request.getRemoteAddr());
		chain.doFilter(request, response);
	}
}
