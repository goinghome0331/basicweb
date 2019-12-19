package kmg.sbr.backend.user.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserForm {
	
	private String username;
	
	private String password;
	
	private String confirmPassword;
	
	private String firstName;

	private String lastName;
	
	private MultipartFile file;
	
	private int year;
	private int month;
	private int day;
	
	private String gender;
	
	public UserForm() {}

	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}



	public MultipartFile getFile() {
		return file;
	}



	public void setFile(MultipartFile file) {
		this.file = file;
	}



	@Override
	public String toString() {
		return "UserForm [username=" + username + ", password=" + password + ", confirmPassword=" + confirmPassword
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", file=" + file + ", year=" + year
				+ ", month=" + month + ", day=" + day + ", gender=" + gender + "]";
	}
	
}
