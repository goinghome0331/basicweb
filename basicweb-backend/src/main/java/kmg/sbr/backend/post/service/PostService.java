package kmg.sbr.backend.post.service;

import java.util.List;

import kmg.sbr.backend.post.dto.Comment;
import kmg.sbr.backend.post.dto.CommentSet;
import kmg.sbr.backend.post.dto.Post;

public interface PostService {
	public List<Post> getPostByIndex(int index) throws Exception;
	public Post getPostById(int id) throws Exception;
	public Comment getCommentById(int id) throws Exception;
	public CommentSet getComments(int id, int index) throws Exception;
	public int saveComment(int postId, String username, String content) throws Exception;
	public int savePost(String username, String title, String content) throws Exception;
	public int deleteComment(int commentId) throws Exception ;
	public int deletePost(int postId) throws Exception;
	public int updatePost(int id, String title, String content) throws Exception;
	public void deleteAllPostByUserId(int userId) throws Exception;
	public void deleteCommentByUserId(int userId) throws Exception;
}
