package kmg.sbr.backend.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {

	public List<Integer> findByUserId(int userId);
	public void save(int userId, int roleId);
	public void deleteByUserId(int userId);
}
