package kmg.sbr.backend.post.dto;

import java.sql.Date;
import java.util.List;

public class Post {
	private int id;
	private String title;
	private String content;
	private int hit;
	private Date date;
	private String username;
	private CommentSet commentSet;
	
	public Post() {}

	
	public static Builder getBuiler() {
		return new Builder();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public CommentSet getCommentSet() {
		return commentSet;
	}


	public void setCommentSet(CommentSet commentSet) {
		this.commentSet = commentSet;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	public static class Builder{
		private Post post;
		public Builder() {
			post = new Post();
		}
		
		public Builder title(String title) {
			post.title = title;
			return this;
		}
		public Builder content(String content) {
			post.content = content;
			return this;
		}
		
		public Builder hit (int hit) {
			post.hit = hit;
			return this;
		}
		public Builder username(String username) {
			post.username = username;
			return this;
		}
		public Post build() {
			return post;
		}
	}

	
	
}

