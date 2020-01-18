package kmg.sbr.backend.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

public class IpLoggerFilter extends OncePerRequestFilter {
	private static Logger logger = Logger.getLogger(IpLoggerFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.infof("request from ip : {}, method : {}", request.getRemoteAddr(), request.getMethod());
		filterChain.doFilter(request, response);
	}
	
	
	
}
