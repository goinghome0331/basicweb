package kmg.sbr.backend.user.service;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import kmg.sbr.backend.exception.DAORuntimeException;
import kmg.sbr.backend.user.dto.User;
import kmg.sbr.backend.user.dto.UserForm;

public interface UserService extends UserDetailsService{
	public User findByUsername(String username) throws DAORuntimeException;
	public String updateUserImage(String username, MultipartFile file) throws IOException, DAORuntimeException;
	public int updateUserPasswd(String username, String currentPassword, String newPassword) throws DAORuntimeException;
	public int deleteUserInfo(String username, String password) throws IOException,DAORuntimeException;
	public void deleteUserImage(String username) throws IOException,DAORuntimeException;
	public int saveUserInfo(UserForm uf) throws IOException,DAORuntimeException;
}
