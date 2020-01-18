package kmg.sbr.backend.controller;

import java.io.IOException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kmg.sbr.backend.exception.DAORuntimeException;
import kmg.sbr.backend.user.dto.JwtRequest;
import kmg.sbr.backend.user.dto.UserForm;
import kmg.sbr.backend.user.service.UserService;
import kmg.sbr.backend.util.JwtTokenUtil;

@RestController
public class SignController {
	@Autowired
	UserService us;

	@Autowired
	private AuthenticationManager am;
	
	@Autowired
	private JwtTokenUtil jtu;
		
	
	private static Logger logger = Logger.getLogger(SignController.class);

	@PostMapping(value = "/signin")
	public String processSignin(@RequestBody JwtRequest authenticationRequest) throws DisabledException,BadCredentialsException,DAORuntimeException{
		
		try {
			am.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
			final UserDetails userDetails = us.loadUserByUsername(authenticationRequest.getUsername());
			String token = jtu.generateToken(userDetails);	
			logger.infof("{} login sucess", userDetails.getUsername());
			return token;
		}catch (DisabledException e) {
			logger.infof("login failed : user_disabled");
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			logger.infof("login failed : invalid_credentials");
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
		
	}
	
	@PostMapping(value = "/signup")
	public int processSignup(@ModelAttribute UserForm uf) throws IOException,DAORuntimeException{
		return us.saveUserInfo(uf);
	}

}
