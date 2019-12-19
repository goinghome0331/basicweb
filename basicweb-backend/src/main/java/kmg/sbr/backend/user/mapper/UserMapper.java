package kmg.sbr.backend.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import kmg.sbr.backend.user.dto.User;

@Mapper
public interface UserMapper {
	public User findByUsername(String username);
	public User findById(int id);
	public String findUsernameById(int id);
	public void save(User user);
	public void delete(int userId);
	public void updateImagePath(String username, String path);
	public void updatePasswd(String username, String password);
}
