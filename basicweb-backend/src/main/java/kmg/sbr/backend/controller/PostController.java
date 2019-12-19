package kmg.sbr.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kmg.sbr.backend.post.dto.Comment;
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
	public List<Post> getPostsByIndex(@RequestParam int index) {
		try {
			return ps.getPostByIndex(index);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/{id}")
	public Post getPostById(@PathVariable int id, @RequestParam int index,@RequestParam boolean rendered) {
		try {
			Post post = ps.getPostById(id,index,rendered);
			return post;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@GetMapping("/{id}/delete-post")
	public int deletePost(@PathVariable int id, @RequestParam String username) {
		AuthenticatedUser au = UserUtil.getAuthenticatedUser();
		if(!au.getUsername().equals(username)) {
			return 0;
		}else {
			try {
				return ps.deletePost(id);
			}catch(Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
	}
	@GetMapping("/comments/{id}/delete-comment")
	public int deleteComment(@PathVariable int id, @RequestParam String username) {
		AuthenticatedUser au = UserUtil.getAuthenticatedUser();
		if(!au.getUsername().equals(username)) {
			return 0;
		}else {
			try {
				return ps.deleteComment(id);
			}catch(Exception e) {
				e.printStackTrace();
				return -1;
			}
			
		}
	}
	@PostMapping("/{id}/add-comment")
	public int addComment(@PathVariable int id,
						@RequestParam String comment) {
		AuthenticatedUser au = UserUtil.getAuthenticatedUser();
		try {
			return ps.saveComment(id, au.getUsername(), comment);
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	@PostMapping("/add-post")
	public int addPost( @RequestParam int id, 
						@RequestParam String title,
						@RequestParam String content) {
		
		AuthenticatedUser au = UserUtil.getAuthenticatedUser();
		try {
			if(id == -1) {
				return ps.savePost(au.getUsername(), title, content);
			}else {
				return ps.updatePost(id, title, content);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
}
