package kmg.sbr.backend.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kmg.sbr.backend.post.dto.Post;
import kmg.sbr.backend.post.dto.PostForm;

@Mapper
public interface PostMapper {
	public int countTotal();
	public int countByTitle(String title);
	public int countByContent(String content);

	public List<Post> findByIndex(int start, int count);
	public List<Post> findByTitle(String title, int start, int count);
	public List<Post> findByContent(String content, int start, int count);
	public Post findById(int id);
	public List<Post> findByUserId(int userId);
	public Post findByIdForComments(int id);
	public void save(PostForm post);
	public void updateHit(int id, int hit);
	public void deleteByUserId(int userId);
	public void deleteById(int id);
	public void updatePost(Post post);
}
