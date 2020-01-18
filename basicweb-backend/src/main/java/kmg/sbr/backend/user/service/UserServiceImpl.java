package kmg.sbr.backend.user.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kmg.sbr.backend.exception.DAORuntimeException;
import kmg.sbr.backend.file.FileInfo;
import kmg.sbr.backend.file.FileService;
import kmg.sbr.backend.post.service.PostService;
import kmg.sbr.backend.user.dto.AuthenticatedUser;
import kmg.sbr.backend.user.dto.Role;
import kmg.sbr.backend.user.dto.User;
import kmg.sbr.backend.user.dto.UserForm;
import kmg.sbr.backend.user.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	private UserMapper um;
	private RoleService rs;
	private PostService ps;
	private PasswordEncoder pe;
	private FileService fs;

	
	public UserServiceImpl(UserMapper um, RoleService rs, PostService ps, PasswordEncoder pe, FileService fs) {
		this.um = um;
		this.rs = rs;
		this.ps = ps;
		this.pe = pe;
		this.fs = fs;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username)throws DAORuntimeException{
		User user = um.findByUsername(username);

		if (user == null) {
			return null;
		}
		return new AuthenticatedUser(user.getUsername(), user.getPassword(), user.getRoles());
	}

	@Override
	public User findByUsername(String username) throws DAORuntimeException{
		User user = um.findByUsername(username);
		if (user == null)
			return null;
		user.setPassword("");
		return user;
	}

	@Transactional(rollbackFor= {IOException.class,DAORuntimeException.class})
	@Override
	public String updateUserImage(String username, MultipartFile file) throws IOException, DAORuntimeException{
		FileInfo fileInfo; 
		
		fileInfo = fs.getPath(file);
		String originalPath = um.findByUsername(username).getImagePath();
		um.updateImagePath(username, fileInfo.getHalfPath());
		fs.replaceFile(originalPath, file,fileInfo);
		
		return fileInfo.getHalfPath();
	}

	@Transactional(rollbackFor=DAORuntimeException.class)
	@Override
	public int updateUserPasswd(String username, String currentPassword, String newPassword) throws DAORuntimeException{
		User user = um.findByUsername(username);
		if (pe.matches(currentPassword, user.getPassword())) {
			um.updatePasswd(username, pe.encode(newPassword));
			return 1;
		} else {
			return 0;
		}

	}

	@Transactional(rollbackFor= {IOException.class,DAORuntimeException.class})
	@Override
	public int deleteUserInfo(String username, String password) throws IOException,DAORuntimeException{
		User user = um.findByUsername(username);
		
		
		if (!pe.matches(password, user.getPassword()))
			return 0;
		
		
		ps.deleteCommentByUserId(user.getId());
		ps.deleteAllPostByUserId(user.getId());
		rs.deleteUserRoleByUserId(user.getId());
		um.delete(user.getId());
		fs.deleteFile(user.getImagePath());
		
		
		return 1;
	}

	@Transactional(rollbackFor= {IOException.class,DAORuntimeException.class})
	@Override
	public void deleteUserImage(String username) throws IOException,DAORuntimeException{
		String filePath = um.findByUsername(username).getImagePath();
		um.updateImagePath(username, null);
		fs.deleteFile(filePath);
	}
	
	@Transactional(rollbackFor= {IOException.class,DAORuntimeException.class})
	@Override
	public int saveUserInfo(UserForm uf) throws IOException,DAORuntimeException{

		User user = um.findByUsername(uf.getUsername());
		if (user != null) {
			return 0;
		}		

		String[] roleNames = { "USER" };
		
		User ret = save(uf, roleNames);
		MultipartFile file = uf.getFile();
		if(file != null && !file.isEmpty())
			updateUserImage(ret.getUsername(), file);
		
		return 1;
	}

	@Transactional(rollbackFor=DAORuntimeException.class)
	private User save(UserForm uf, String[] roleNames) throws DAORuntimeException{
		List<Role> roles = rs.findAll();
		
		Iterator<Role> it = roles.iterator();
		while (it.hasNext()) {
			Role role = it.next();
			boolean ch = false;
			for (String roleName : roleNames) {
				if (roleName.equals(role.getName()))
					ch = true;
			}
			if (!ch)
				it.remove();
		}
		
		Calendar c = Calendar.getInstance();
		c.set(uf.getYear(), uf.getMonth() - 1, uf.getDay());
		Date d = c.getTime();
		java.sql.Date birth = new java.sql.Date(d.getTime());
		
		
		User u = User.getBuilder().username(uf.getUsername()).password(pe.encode(uf.getPassword()))
				.firstName(uf.getFirstName()).lastName(uf.getLastName()).birth(birth).gender(uf.getGender())
				.roles(roles).build();
		
		
		um.save(u);
		rs.save(u.getId(), roles);

		logger.infof("{} user signup completed", u.getUsername());

		return u;
	}

}
