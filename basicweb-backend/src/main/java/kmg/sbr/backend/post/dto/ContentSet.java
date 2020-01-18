package kmg.sbr.backend.post.dto;

public class ContentSet {
	public static final int COUNT_IN_PAGE = 10;
	public static final int PAGE_IN_BOARD = 10;

	private int bsi; //출력할 글 인덱스 
	private int total;
	
	
	
	

	
	public int getBsi() {
		return bsi;
	}
	public void setBsi(int bsi) {
		this.bsi = bsi;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total, int bpci, boolean acend) {
		this.total = total;
		if(acend) {
			setBpciForAcend(bpci);
		}else {
			setBpciForDecend(bpci);
		}
	}
	private void setBpciForAcend(int bpci) {
		if(total ==0) {
			bsi = 0;
			return ;
		}
		bsi = (bpci - 1) * COUNT_IN_PAGE;
		
	}
	private void setBpciForDecend(int bpci) {
		if(total ==0) {
			bsi = 0;
			return ;
		}
		bsi = total - (bpci - 1) * COUNT_IN_PAGE;
		int tp = total / COUNT_IN_PAGE;
		tp = total % COUNT_IN_PAGE == 0 ? tp : tp + 1;  
		if(bpci > tp) bsi = tp;
		else bsi = 0;
	}
	
}
