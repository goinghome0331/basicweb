package kmg.sbr.backend.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import kmg.sbr.backend.user.dto.AuthenticatedUser;
import kmg.sbr.backend.user.dto.User;

public class UserUtil {
	public static void signin(User user) {
		AuthenticatedUser au = new AuthenticatedUser(user.getUsername(), user.getPassword(), user.getRoles());

        Authentication authentication = new UsernamePasswordAuthenticationToken(au, null, au.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        
	}
	public static AuthenticatedUser getAuthenticatedUser() {
		Object token = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(token instanceof AuthenticatedUser) {
			AuthenticatedUser au = (AuthenticatedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return au;
		}
		return null;
	}
}
