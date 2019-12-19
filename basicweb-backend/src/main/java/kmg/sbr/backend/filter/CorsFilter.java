package kmg.sbr.backend.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter{
	private static Logger logger = Logger.getLogger(CorsFilter.class);
	
	 @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
	        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
	        
	        
	        StringBuilder sb = new StringBuilder();
	        Enumeration<String> e =  request.getHeaderNames();
	        
	        while(e.hasMoreElements()) {
	        	String headerName = e.nextElement();
	        	String headerValue = request.getHeader(headerName);
	        	sb.append(" ["+headerName+"] : " + headerValue);
	        }
	        
	        logger.infof("request from ip {} method : {}, header : {}", request.getRemoteAddr(), request.getMethod(), sb.toString());
	        
	        if ("OPTIONS".equals(request.getMethod())) {
	            response.setStatus(HttpServletResponse.SC_OK);
	        } else { 
	            filterChain.doFilter(request, response);
	        }
	    }
}
