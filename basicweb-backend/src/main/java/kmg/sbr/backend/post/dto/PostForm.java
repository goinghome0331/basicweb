package kmg.sbr.backend.post.dto;

public class PostForm {
	
	private int id;
	
	private String title;
	
	private String content;
	
	private int hit;
	
	private int userId;
	
	public PostForm() {}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getHit() {
		return hit;
	}


	public void setHit(int hit) {
		this.hit = hit;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
}
