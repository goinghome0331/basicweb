package kmg.sbr.backend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kmg.sbr.backend.exception.DAORuntimeException;
import kmg.sbr.backend.user.dto.AuthenticatedUser;
import kmg.sbr.backend.user.dto.User;
import kmg.sbr.backend.user.service.UserService;
import kmg.sbr.backend.util.UserUtil;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService us;
	
	@GetMapping(value="/info")
	public User getUserInfo(@RequestParam String username) throws DAORuntimeException{
		return us.findByUsername(username);
	}
	@PostMapping(value="/update-password")
	public int updatePassword(@RequestParam("currentPassword") String currentPassword,
								 @RequestParam("newPassword") String newPassword) throws DAORuntimeException{
		AuthenticatedUser au = UserUtil.getAuthenticatedUser();
		return us.updateUserPasswd(au.getUsername(), currentPassword, newPassword);
	}
	@PostMapping(value="/delete-user")
	public int deleteUser(@RequestParam("deletePassword") String password) throws IOException,DAORuntimeException{
		AuthenticatedUser au = UserUtil.getAuthenticatedUser();
		return us.deleteUserInfo(au.getUsername(), password);
	}
}
