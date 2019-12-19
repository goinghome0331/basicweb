package kmg.sbr.backend.post.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kmg.sbr.backend.file.FileService;
import kmg.sbr.backend.post.dto.Comment;
import kmg.sbr.backend.post.dto.CommentForm;
import kmg.sbr.backend.post.dto.ContentSet;
import kmg.sbr.backend.post.dto.Post;
import kmg.sbr.backend.post.dto.PostForm;
import kmg.sbr.backend.post.mapper.CommentMapper;
import kmg.sbr.backend.post.mapper.PostMapper;
import kmg.sbr.backend.user.dto.User;
import kmg.sbr.backend.user.mapper.UserMapper;

@Service
public class PostServiceImpl implements PostService {

	
	@Autowired
	private UserMapper um;
	
	
	@Autowired
	private PostMapper pm;

	
	@Autowired
	private CommentMapper cm;
	
	
	@Autowired
	private FileService fs;
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public List<Post> getPostByIndex(int index) throws Exception{
		ContentSet cs = new ContentSet();
		cs.setTotal(pm.countTotal(), index,false);
		if(cs.getTotal() == 0) return new LinkedList<Post>();
		List<Post> posts = pm.findByIndex(cs.getBsi(), cs.getCic());
		posts.sort(new PostSorter());
		return posts;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public Post getPostById(int id, int index,boolean rendered) throws Exception{
		Post post = pm.findById(id);
		if(rendered) {
			post.setHit(post.getHit()+1);
			pm.updateHit(id, post.getHit());
		}
		
		ContentSet cs = new ContentSet();
		cs.setTotal(cm.countByPostId(id), index,true);
		post.setTotal(cs.getTotal());
		List<Comment> comments = cm.findByPostId(id, cs.getBsi(), cs.getCic());
		if(comments.isEmpty()) {
			post.setComments(null);
		}else {
			HashMap<String,String> map = new HashMap<String,String>();
			Iterator<Comment> it = comments.iterator();
			while(it.hasNext()) {
				Comment c = it.next();
				User u = um.findByUsername(c.getUsername());
				String imageData = map.get(u.getImagePath());
				if(imageData == null) {
					imageData = fs.getBase64DataOfImage(u.getImagePath());
					map.put(u.getImagePath(),imageData);
				}
				c.setImageData(imageData);
			}
			post.setComments(comments);
		}
		return post;
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int saveComment(int postId, String username, String content) throws Exception{
		User user = um.findByUsername(username);
		CommentForm comment = new CommentForm();
		comment.setPostId(postId);
		comment.setUserId(user.getId());
		comment.setContent(content);
		cm.save(comment);
		return comment.getId();
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int savePost(String username, String title, String content) throws Exception{
		User user = um.findByUsername(username);
		PostForm post = new PostForm();
		post.setTitle(title);
		post.setContent(content);
		post.setHit(0);
		post.setUserId(user.getId());
		pm.save(post);
		return post.getId();
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int updatePost(int id, String title, String content) throws Exception{
		Post post = pm.findById(id);
		post.setTitle(title);
		post.setContent(content);
		pm.updatePost(post);
		return post.getId();
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int deleteComment(int commentId) throws Exception{
		cm.deleteById(commentId);
		return 1;
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int deletePost(int postId) throws Exception{
		cm.deleteByPostId(postId);
		pm.deleteById(postId);
		return 1;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteAllPostByUserId(int userId) throws Exception{
		List<Post> posts = pm.findByUserId(userId);
		for (Post post : posts) {
			cm.deleteByPostId(post.getId());
			pm.deleteById(post.getId());
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteCommentByUserId(int userId) throws Exception{
		cm.deleteByUserId(userId);
	}
	
	
	public static class PostSorter implements Comparator<Post> {
		@Override
		public int compare(Post o1, Post o2) {
			return Integer.compare(o2.getId(), o1.getId());
		}

	}
	
}
