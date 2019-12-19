package kmg.sbr.backend.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import kmg.sbr.backend.file.FileInfo;
import kmg.sbr.backend.file.FileService;
import kmg.sbr.backend.post.service.PostService;
import kmg.sbr.backend.user.dto.Role;
import kmg.sbr.backend.user.dto.User;
import kmg.sbr.backend.user.dto.UserForm;
import kmg.sbr.backend.user.mapper.UserMapper;
import kmg.sbr.backend.user.service.RoleService;
import kmg.sbr.backend.user.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@TestPropertySource(properties="file.home-dir=${java.io.tmpdir}basicweb/")
public class UserServiceTest {
	
	@Autowired
	Environment env;

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserMapper userMapper;

	@Mock
	private RoleService roleService;
	
	@Mock
	private PostService postService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private FileService fs;
	
	
	
	static final String TEST_USERNAME = "kmgeu";
	static final String TEST_IMAGEPATH = "uploads/kmgeu/1.jpg";
	static final String TEST_NEW_USERNAME = "kmgeu123";
	static final String TEST_NEW_IMAGEPATH = "uploads/kmgeu/13강민규.jpg";
	
	private User user;
	@Before
	public void set() {
		user = new User.Builder()
				.username(TEST_USERNAME)
				.password("12345678")
				.birth(new Date(0))
				.firstName("형규")
				.lastName("강")
				.gender("남")
				.build();
		user.setId(1);
		user.setImagePath(TEST_IMAGEPATH);
		
		Role role = new Role();
		role.setId(2);
		role.setName("USER");
		
		List<Role> roles= new LinkedList<Role>();
		roles.add(role);
		user.setRoles(roles);
		
		when(userMapper.findByUsername(TEST_USERNAME)).thenReturn(user);
		// BCryptPasswordEncoder
		// 12345678 : b$2a$10$LKeAYfCVkWkZG57crx7M6ObK6z4rJthrHyaKkkZQFXJ4DLqe.usq2
		// asdfasdf : $2a$10$LvOlpNNjt9ElTE2.hk1/bOwBxjp3NoEA35hgYt0EtYbUZNBXY/Fue
	}
	
	@Test
	public void testLoadUser() {
		UserDetails au = userService.loadUserByUsername(TEST_USERNAME);
		
		assertTrue(au.getUsername().equals(TEST_USERNAME));
		assertTrue(au.getPassword().equals("12345678"));
		verify(userMapper).findByUsername(TEST_USERNAME);
	}
	
	@Test(expected=Exception.class)
	public void testLoadUserIfExceptionExist() {
		userService.loadUserByUsername("kmgeu123");
		verify(userMapper).findByUsername("kmgeu123");
	}
	
	
	@Test
	public void testUpdatePassword() throws Exception {
		when(passwordEncoder.matches("12345678", "12345678")).thenReturn(true);
		when(passwordEncoder.encode("asdfasdf")).thenReturn("asdfasdf");
		int ret = userService.updateUserPasswd(TEST_USERNAME, "12345678", "asdfasdf");
		
		verify(userMapper).updatePasswd(TEST_USERNAME, "asdfasdf");
		
		assertTrue(ret == 1);
	}
	
	@Test
	public void testUpdatePasswordIfPasswordNotMatch() throws Exception {
		when(passwordEncoder.matches("12345678", "12345678")).thenReturn(true);
		int ret = userService.updateUserPasswd(TEST_USERNAME, "1234567", "asdfasdf");
		
		assertTrue(ret == 0);
	}
	
	@Test
	public void testRemoveUserImage() throws Exception{
		userService.deleteUserImage(TEST_USERNAME);
		
		verify(userMapper).updateImagePath(TEST_USERNAME, null);
		verify(fs).deleteFile(TEST_IMAGEPATH);
	}
	
	@Test
	public void testRemoveUserImageIfFileNuLL() throws Exception{
		user.setImagePath(null);
		when(fs.deleteFile(null)).thenReturn(false);
		
		userService.deleteUserImage(TEST_USERNAME);
		
		verify(userMapper).updateImagePath(TEST_USERNAME, null);
		verify(fs).deleteFile(null);
	}	
	
	@Test
	public void testUpdateUserImage() throws Exception{
		MultipartFile mockFile = getMockMultiPartFile();
		
		Path path = Paths.get(env.getProperty("file.home-dir")).resolve(TEST_NEW_IMAGEPATH);
		FileInfo fileInfo = new FileInfo(path,TEST_NEW_IMAGEPATH);
		when(fs.getPath(mockFile)).thenReturn(fileInfo);
		
		String ret = userService.updateUserImage(TEST_USERNAME,mockFile);
		
		verify(userMapper).updateImagePath(TEST_USERNAME, TEST_NEW_IMAGEPATH);
		verify(fs).replaceFile(TEST_IMAGEPATH, mockFile, fileInfo);
		assertTrue(ret.equals(TEST_NEW_IMAGEPATH));
	}
	
	@Test
	public void testDeleteUserInfo() throws Exception{
		when(passwordEncoder.matches("12345678", "12345678")).thenReturn(true);
		
		int ret = userService.deleteUserInfo(TEST_USERNAME, "12345678");
		
		assertTrue(ret == 1);
		
		
		verify(postService).deleteCommentByUserId(1);
		verify(postService).deleteAllPostByUserId(1);
		verify(roleService).deleteUserRoleByUserId(1);
		verify(userMapper).delete(1);
		verify(fs).deleteFile(TEST_IMAGEPATH);
		
	}
	@Test
	public void testDeleteUserInfoIfPasswordNotMatch() throws Exception{
		when(passwordEncoder.matches("12345678", "12345678")).thenReturn(true);
		int ret = userService.deleteUserInfo(TEST_USERNAME, "1234567");
		assertTrue(ret == 0);
		verifyZeroInteractions(postService);
		verifyZeroInteractions(roleService);
		verifyZeroInteractions(fs);
	}
	
	@Test
	public void testSaveUserInfo() throws Exception{
		List<Role> roles = getUserRoles();
		when(roleService.findAll()).thenReturn(roles);
		when(passwordEncoder.encode("asdfasdf")).thenReturn("asdfasdf");
		
		MultipartFile mockFile = getMockMultiPartFile();
		UserForm uf = getUserForm();
		User newUser = getNewUser();
		
		uf.setFile(mockFile);
		
		newUser.setRoles(roles);
		when(userMapper.findByUsername("kmgeu123")).thenAnswer(new Answer() {
			private boolean toggle = false;
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				if(!toggle) {
					toggle = true;
					return null;
				}
				return newUser;
			}
		
		});
		Path path = Paths.get(env.getProperty("file.home-dir")).resolve(TEST_NEW_IMAGEPATH);
		FileInfo fileInfo = new FileInfo(path,TEST_NEW_IMAGEPATH);
		when(fs.getPath(mockFile)).thenReturn(fileInfo);
		
		
		
		
		userService.saveUserInfo(uf);
		
		verify(userMapper).save(newUser);
		verify(roleService).save(0, roles);
		verify(userMapper).updateImagePath(TEST_NEW_USERNAME, TEST_NEW_IMAGEPATH);
		verify(fs).replaceFile(null, mockFile, fileInfo);
	}
	
	@Test
	public void testSaveUserInfoIfUserExist() throws Exception{
		List<Role> roles = getUserRoles();
		User newUser = getNewUser();
		newUser.setRoles(roles);
		UserForm uf = getUserForm();
		
		when(roleService.findAll()).thenReturn(roles);
		when(passwordEncoder.encode("asdfasdf")).thenReturn("asdfasdf");
		when(userMapper.findByUsername("kmgeu123")).thenReturn(newUser);
		
		userService.saveUserInfo(uf);
		verifyZeroInteractions(roleService);
		verifyZeroInteractions(passwordEncoder);
	}
	
	@Test
	public void testSaveUserInfoIfImageNotExist() throws Exception{
		List<Role> roles = getUserRoles();
		User newUser = getNewUser();
		newUser.setRoles(roles);
		UserForm uf = getUserForm();
		
		when(roleService.findAll()).thenReturn(roles);
		when(passwordEncoder.encode("asdfasdf")).thenReturn("asdfasdf");
		
		newUser.setRoles(roles);
		when(userMapper.findByUsername("kmgeu123")).thenAnswer(new Answer() {
			private boolean toggle = false;
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				if(!toggle) {
					toggle = true;
					return null;
				}
				return newUser;
			}
		
		});
		
		userService.saveUserInfo(uf);
		
		verifyZeroInteractions(fs);
	}
	
	
	
	
	
	
	
	
	
	
	List<Role> getUserRoles(){
		List<Role> roles = new LinkedList<Role>();
		Role role = new Role();
		role.setName("USER");
		role.setId(2);
		roles.add(role);
		return roles;
	}
	MultipartFile getMockMultiPartFile() {
		return new MockMultipartFile("test", "test.txt", "text/plain", "test data".getBytes());
	}
	UserForm getUserForm() {
		UserForm uf = new UserForm();
		uf.setUsername(TEST_NEW_USERNAME);
		uf.setPassword("asdfasdf");
		uf.setFirstName("민규");
		uf.setLastName("강");
		uf.setGender("남");
		uf.setYear(1992);
		uf.setMonth(11);
		uf.setDay(25);
		return uf;
	}
	User getNewUser() {
		Calendar c = Calendar.getInstance();
		c.set(1992, 10, 25);
		java.util.Date d = c.getTime();
		java.sql.Date birth = new java.sql.Date(d.getTime());
		
		User newUser = new User.Builder()
				.username("kmgeu123")
				.password("asdfasdf")
				.birth(birth)
				.firstName("민규")
				.lastName("강")
				.gender("남")
				.build();
		return newUser;
	}
}

