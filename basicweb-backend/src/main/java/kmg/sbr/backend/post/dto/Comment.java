package kmg.sbr.backend.post.dto;

import java.sql.Date;

import kmg.sbr.backend.user.dto.User;

public class Comment {
	private int id;
	private String content;
	private Date regDate;
	private String imageData;
	private String username;
	private int postId;
	
	
	public String getImageData() {
		return imageData;
	}
	public void setImageData(String imageData) {
		this.imageData = imageData;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", regDate=" + regDate + ", username=" + username
				+ ", postId=" + postId + "]";
	}
	
	
	
}
