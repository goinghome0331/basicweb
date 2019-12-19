package kmg.sbr.backend.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kmg.sbr.backend.user.dto.Role;

@Mapper
public interface RoleMapper {
	public Role findByName(String name);
	public Role findById(int id);
	public Role findByUserId(int userId);
	public List<Role> findAll();
}
