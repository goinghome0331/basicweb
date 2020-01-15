package kmg.sbr.backend.post.dto;

import java.util.List;

public class CommentSet {
	private int total;
	private List<Comment> comments;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
}
