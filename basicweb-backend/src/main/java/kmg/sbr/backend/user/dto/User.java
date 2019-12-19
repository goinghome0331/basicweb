package kmg.sbr.backend.user.dto;

import java.sql.Date;
import java.util.List;

public class User {
	private int id;
	private String username;
	private String password;
	private String gender;
	private String firstName;
	private String lastName;
	private Date birth;
	
	private String imagePath;
	private List<Role> roles;

	public static Builder getBuilder() {
		return new Builder();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public static class Builder {
		User user;

		public Builder() {
			user = new User();
		}

		public Builder username(String username) {
			user.username = username;
			return this;
		}

		public Builder password(String password) {
			user.password = password;
			return this;
		}

		public Builder gender(String gender) {
			user.gender = gender;
			return this;
		}
		public Builder firstName(String firstName) {
			user.firstName = firstName;
			return this;
		}
		public Builder lastName(String lastName) {
			user.lastName = lastName;
			return this;
		}
		public Builder birth(Date birth) {
			user.birth = birth;
			return this;
		}
		public Builder roles(List<Role> roles) {
			user.roles = roles;
			return this;
		}

		public User build() {
			return user;
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", gender=" + gender
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", birth=" + birth + ", imagePath="
				+ imagePath + ", roles=" + roles + "]";
	}

	@Override
	public boolean equals(Object obj) {
		User user = (User)obj;
		if(this.username.equals(user.username)) {
			return true;
		}else {
			return false;
		}
	}

	

}
