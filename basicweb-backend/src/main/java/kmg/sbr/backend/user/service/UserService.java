package kmg.sbr.backend.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import kmg.sbr.backend.user.dto.User;
import kmg.sbr.backend.user.dto.UserForm;

public interface UserService extends UserDetailsService{
	public int saveUserInfo(UserForm uf) throws Exception;
	public void deleteUserImage(String username) throws Exception;
	public String updateUserImage(String username, MultipartFile file) throws Exception ;
	public User findByUsername(String username) throws Exception;
	public int deleteUserInfo(String username, String password) throws Exception;
	public int updateUserPasswd(String username, String currentPassword, String newPassword) throws Exception;
}
