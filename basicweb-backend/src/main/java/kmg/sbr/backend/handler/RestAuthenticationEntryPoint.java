package kmg.sbr.backend.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{

	private static Logger logger = Logger.getLogger(RestAuthenticationEntryPoint.class);
	
	@Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
		logger.infof("authentication fail from ip : {}",request.getRemoteAddr());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
