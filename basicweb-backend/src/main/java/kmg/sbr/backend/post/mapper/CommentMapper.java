package kmg.sbr.backend.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kmg.sbr.backend.post.dto.Comment;
import kmg.sbr.backend.post.dto.CommentForm;

@Mapper
public interface CommentMapper {
	public int countByPostId(int postId);
	public void save(CommentForm comment);
	public List<Comment> findByPostId(int postId, int start, int count);
	public Comment findById(int id);
	public void deleteByUserId(int userId);
	public void deleteByPostId(int postId);
	public void deleteById(int id);
}
