package kmg.sbr.backend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kmg.sbr.backend.exception.DAORuntimeException;
import kmg.sbr.backend.file.FileService;
import kmg.sbr.backend.user.dto.AuthenticatedUser;
import kmg.sbr.backend.user.dto.User;
import kmg.sbr.backend.user.service.UserService;
import kmg.sbr.backend.util.UserUtil;

@RestController
@RequestMapping("/files")
public class FileController {
	
	@Autowired
	UserService us;
	
	@Autowired
	FileService fs;
	
	@GetMapping(value="/delete-user-image")
	public boolean deleteImage() throws IOException, DAORuntimeException{
		AuthenticatedUser au  = UserUtil.getAuthenticatedUser();
		us.deleteUserImage(au.getUsername());
		return true;
	}
	
	@GetMapping(value="/user-image")
	public String getImage(@RequestParam("username") String username) throws IOException{
		User user = us.findByUsername(username);
		return fs.getBase64DataOfImage(user.getImagePath());
	}
	
	@PostMapping(value="/update-user-image")
	public String updateUserImage(@RequestParam(value="file") MultipartFile file) throws IOException, DAORuntimeException{
		AuthenticatedUser au  = UserUtil.getAuthenticatedUser();
		return us.updateUserImage(au.getUsername(),file);
	}
}
