package kmg.sbr.backend.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kmg.sbr.backend.user.dto.Role;
import kmg.sbr.backend.user.mapper.RoleMapper;
import kmg.sbr.backend.user.mapper.UserRoleMapper;

@Service
public class RoleServiceImpl implements RoleService {

	private RoleMapper rm;
	private UserRoleMapper urm;
	
	
	public RoleServiceImpl(RoleMapper rm, UserRoleMapper urm) {
		this.rm = rm;
		this.urm = urm;
	}
	
	
	@Transactional
	@Override
	public void deleteUserRoleByUserId(int userId) {
		urm.deleteByUserId(userId);
	}

	
	@Override
	public List<Role> findAll() {
		return rm.findAll();
	}

	@Transactional
	@Override
	public void save(int userId, List<Role> roles) {
		for (Role role : roles) {
			urm.save(userId, role.getId());
		}
	}

}
