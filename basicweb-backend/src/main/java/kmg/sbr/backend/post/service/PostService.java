package kmg.sbr.backend.post.service;

import java.io.IOException;
import java.util.List;

import kmg.sbr.backend.exception.DAORuntimeException;
import kmg.sbr.backend.post.dto.CommentSet;
import kmg.sbr.backend.post.dto.Post;

public interface PostService {
	public List<Post> getPostByIndex(int index)throws DAORuntimeException;
	public Post getPostById(int id) throws IOException, DAORuntimeException;
	public CommentSet getComments(int id, int index) throws IOException, DAORuntimeException;
	public int saveComment(int postId, String username, String content) throws DAORuntimeException;
	public int savePost(String username, String title, String content) throws DAORuntimeException;
	public int deleteComment(int commentId) throws DAORuntimeException;
	public int deletePost(int postId) throws DAORuntimeException;
	public void deleteAllPostByUserId(int userId) throws DAORuntimeException;
	public int updatePost(int id, String title, String content) throws DAORuntimeException;
	public void deleteCommentByUserId(int userId)throws DAORuntimeException;
}
