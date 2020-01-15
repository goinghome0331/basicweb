package kmg.sbr.backend.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmg.sbr.backend.Result;
import kmg.sbr.backend.user.dto.AuthenticatedUser;
import kmg.sbr.backend.user.dto.UserForm;
import kmg.sbr.backend.user.service.UserService;
import kmg.sbr.backend.util.UserUtil;


@RestController
public class SignController {
	@Autowired
	UserService us;

	private static Logger logger = Logger.getLogger(SignController.class);
	
	@RequestMapping(value="/signin")
	public boolean processSignin() {
		AuthenticatedUser au  = UserUtil.getAuthenticatedUser();
		logger.infof("{} login sucess", au.getUsername());
		return true;
	}
	
	@PostMapping(value="/signup")
	public int processSignup(@ModelAttribute UserForm uf) throws Exception{
		return us.saveUserInfo(uf);
	}
	
}
