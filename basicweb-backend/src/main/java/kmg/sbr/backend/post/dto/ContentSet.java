package kmg.sbr.backend.post.dto;

public class ContentSet {
	public static final int COUNT_IN_PAGE = 10;
	public static final int PAGE_IN_BOARD = 10;
	private int bpli; // 보드의 마지막 페이지 시작 인덱스
	private int tp; // 마지막 페이지 인덱스이자 총 페이지 수
	private int bsi; //출력할 글 인덱스 
	private int bpsi; // 페이지 시작 인덱스
	private int bpei; // 페이지 끝 인덱스
	private int bpci; // 페이지 현재 인덱스
	private int cic; // 현재 페이지의 갯수
	private int total; // 게시글 총 갯수
	
	public ContentSet() {
		bpsi = 1;
		bpci = 1;
	}

	public int getBpsi() {
		return bpsi;
	}

	public int getCic() {
		return cic;
	}
	
	public int getBpei() {
		return bpei;
	}

	public int getBpci() {
		return bpci;
	}
	public int getTotal() {
		return total;
	}
	public int getBsi() {
		return bsi;
	}
	public int getBpli() {
		return bpli;
	}
	public int getTp() {
		return tp;
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
			tp = bsi = cic = 0;
			return ;
		}
		this.bpci = bpci;
		bsi = (bpci - 1) * COUNT_IN_PAGE;
		tp = total / COUNT_IN_PAGE;
		if(total % COUNT_IN_PAGE != 0) tp++;
		if(total % COUNT_IN_PAGE == 0)
			cic = COUNT_IN_PAGE;
		else {
			if(bpci == tp)
				cic = total % COUNT_IN_PAGE;
			else
				cic = COUNT_IN_PAGE;
		}
	}
	private void setBpciForDecend(int bpci) {
		if(total ==0) {
			tp = bsi = cic = 0;
			bpsi = this.bpci = bpei = 1;
			return ;
		}
		this.bpci = bpci;
		bsi = total - (bpci - 1) * COUNT_IN_PAGE;
//		bsi = (bpci - 1) * COUNT_IN_PAGE;
		bpsi = (bpci - 1) / PAGE_IN_BOARD * PAGE_IN_BOARD + 1;
		
		tp = total / COUNT_IN_PAGE;
		if(total % COUNT_IN_PAGE != 0) tp++;
		
		bpli = tp - tp % COUNT_IN_PAGE + 1;
		
		bpei = bpsi+(PAGE_IN_BOARD - 1);
		if(bpei > tp) bpei = (bpsi - 1) + tp % PAGE_IN_BOARD;
		
		
		if(total % COUNT_IN_PAGE == 0)
			cic = COUNT_IN_PAGE;
		else {
			if(bpci == tp)
				cic = total % COUNT_IN_PAGE;
			else
				cic = COUNT_IN_PAGE;
		}
		bsi -= cic;
	}
	



	@Override
	public String toString() {
		return "Board [bsi=" + bsi + ", bpsi=" + bpsi + ", bpei=" + bpei + ", bpci=" + bpci + ", cic=" + cic
				+ ", total=" + total + "]";
	}
}
