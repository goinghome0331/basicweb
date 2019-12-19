package kmg.sbr.backend.user.service;

import java.util.List;

import kmg.sbr.backend.user.dto.Role;

public interface RoleService {
	public void deleteUserRoleByUserId(int userId);
	public List<Role> findAll();
	public void save(int userId, List<Role> roles);
}
