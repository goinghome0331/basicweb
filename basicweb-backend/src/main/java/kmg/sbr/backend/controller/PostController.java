package kmg.sbr.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kmg.sbr.backend.exception.DAORuntimeException;
import kmg.sbr.backend.post.dto.CommentSet;
import kmg.sbr.backend.post.dto.Post;
import kmg.sbr.backend.post.service.PostService;
import kmg.sbr.backend.user.dto.AuthenticatedUser;
import kmg.sbr.backend.util.UserUtil;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired 
	private PostService ps;
	
	@GetMapping("/get-by-index")
	public List<Post> getPostsByIndex(@RequestParam int index) throws DAORuntimeException{
		return ps.getPostByIndex(index);
	}
	
	@GetMapping("/{id}")
	public Post getPostById(@PathVariable int id) throws IOException,DAORuntimeException{
		return ps.getPostById(id);
	}
	
	@GetMapping("comments-by-postId/{id}")
	public CommentSet getPostById(@PathVariable int id, @RequestParam int index) throws IOException,DAORuntimeException{
		return ps.getComments(id, index);
	}
	@GetMapping("/{id}/delete-post")
	public int deletePost(@PathVariable int id, @RequestParam String username) throws DAORuntimeException{
		AuthenticatedUser au = UserUtil.getAuthenticatedUser();
		if(!au.getUsername().equals(username)) {
			return 0;
		}else{
			return ps.deletePost(id);
		}
	}
	@GetMapping("/comments/{id}/delete-comment")
	public int deleteComment(@PathVariable int id, @RequestParam String username) throws DAORuntimeException{
		AuthenticatedUser au = UserUtil.getAuthenticatedUser();
		if(!au.getUsername().equals(username)) {
			return 0;
		}else {
			return ps.deleteComment(id);		
		}
	}
	@PostMapping("/{id}/add-comment")
	public int addComment(@PathVariable int id,
						@RequestParam String comment) throws DAORuntimeException{
		AuthenticatedUser au = UserUtil.getAuthenticatedUser();
		return ps.saveComment(id, au.getUsername(), comment);		
	}
	@PostMapping("/add-post")
	public int addPost( @RequestParam int id, 
						@RequestParam String title,
						@RequestParam String content) throws DAORuntimeException{
		if(id == -1) {
			AuthenticatedUser au = UserUtil.getAuthenticatedUser();
			return ps.savePost(au.getUsername(), title, content);
		}else {
			return ps.updatePost(id, title, content);
		}
	}
	
	
}
