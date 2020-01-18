package kmg.sbr.backend.post.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kmg.sbr.backend.exception.DAORuntimeException;
import kmg.sbr.backend.file.FileService;
import kmg.sbr.backend.post.dto.Comment;
import kmg.sbr.backend.post.dto.CommentForm;
import kmg.sbr.backend.post.dto.CommentSet;
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
	
	
	@Override
	public List<Post> getPostByIndex(int index)throws DAORuntimeException{
		ContentSet cs = new ContentSet();
		cs.setTotal(pm.countTotal(), index,false);
		if(cs.getTotal() == 0) return new LinkedList<Post>();
		List<Post> posts = pm.findByIndex(cs.getBsi(), ContentSet.PAGE_IN_BOARD);
		posts.sort(new PostSorter());
		return posts;
	}
	
	@Transactional(rollbackFor= {IOException.class, DAORuntimeException.class})
	@Override
	public Post getPostById(int id) throws IOException, DAORuntimeException{
		Post post = pm.findById(id);
		post.setHit(post.getHit()+1);
		pm.updateHit(id, post.getHit());
		
		ContentSet cs = new ContentSet();
		cs.setTotal(cm.countByPostId(id), 1,true);
		
		List<Comment> comments = cm.findByPostId(id, cs.getBsi(), ContentSet.COUNT_IN_PAGE);
		
		
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
		
		CommentSet commentSet = new CommentSet();
		commentSet.setTotal(cs.getTotal());
		commentSet.setComments(comments);
		post.setCommentSet(commentSet);
		return post;
	}
	@Transactional(rollbackFor= {IOException.class, DAORuntimeException.class})
	@Override
	public CommentSet getComments(int id, int index) throws IOException, DAORuntimeException{
		ContentSet cs = new ContentSet();
		cs.setTotal(cm.countByPostId(id), index,true);
		List<Comment> comments = cm.findByPostId(id, cs.getBsi(), ContentSet.COUNT_IN_PAGE);
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
		
		CommentSet commentSet = new CommentSet();
		commentSet.setTotal(cs.getTotal());
		commentSet.setComments(comments);
		return commentSet;
	}
	
	@Transactional(rollbackFor=DAORuntimeException.class)
	@Override
	public int saveComment(int postId, String username, String content) throws DAORuntimeException{
		User user = um.findByUsername(username);
		CommentForm comment = new CommentForm();
		comment.setPostId(postId);
		comment.setUserId(user.getId());
		comment.setContent(content);
		cm.save(comment);
		return comment.getId();
	}
	
	@Transactional(rollbackFor=DAORuntimeException.class)
	@Override
	public int savePost(String username, String title, String content) throws DAORuntimeException{
		User user = um.findByUsername(username);
		PostForm post = new PostForm();
		post.setTitle(title);
		post.setContent(content);
		post.setHit(0);
		post.setUserId(user.getId());
		pm.save(post);
		return post.getId();
	}
	@Transactional(rollbackFor=DAORuntimeException.class)
	@Override
	public int updatePost(int id, String title, String content) throws DAORuntimeException{
		Post post = pm.findById(id);
		post.setTitle(title);
		post.setContent(content);
		pm.updatePost(post);
		return post.getId();
	}
	@Transactional(rollbackFor=DAORuntimeException.class)
	@Override
	public int deleteComment(int commentId) throws DAORuntimeException{
		cm.deleteById(commentId);
		return 1;
	}
	@Transactional(rollbackFor=DAORuntimeException.class)
	@Override
	public int deletePost(int postId) throws DAORuntimeException{
		cm.deleteByPostId(postId);
		pm.deleteById(postId);
		return 1;
	}
	
	@Transactional(rollbackFor=DAORuntimeException.class)
	@Override
	public void deleteAllPostByUserId(int userId) throws DAORuntimeException{
		List<Post> posts = pm.findByUserId(userId);
		for (Post post : posts) {
			cm.deleteByPostId(post.getId());
			pm.deleteById(post.getId());
		}
	}
	
	@Transactional(rollbackFor=DAORuntimeException.class)
	@Override
	public void deleteCommentByUserId(int userId)throws DAORuntimeException{
		cm.deleteByUserId(userId);
	}
	
	
	public static class PostSorter implements Comparator<Post> {
		@Override
		public int compare(Post o1, Post o2) {
			return Integer.compare(o2.getId(), o1.getId());
		}

	}
	
}
