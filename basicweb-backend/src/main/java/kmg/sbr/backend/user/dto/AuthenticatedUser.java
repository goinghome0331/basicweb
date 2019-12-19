package kmg.sbr.backend.user.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUser implements UserDetails{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private List<Role> roles;
	
	

	public AuthenticatedUser(String username, String password, List<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
	@Override
	public String getPassword() {
		return password;
	}



	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String[] roleNames = new String[roles.size()];
		
		for(int i = 0 ; i < roles.size(); i++) {
			roleNames[i] = roles.get(i).getName();
		}
		return AuthorityUtils.createAuthorityList(roleNames);
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
