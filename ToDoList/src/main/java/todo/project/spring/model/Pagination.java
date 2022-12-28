package todo.project.spring.model;

import lombok.Data;

@Data
public class Pagination {
	private int worksViewLimit;
	private int startPage;
	private int endPage;
	private int totalWorks;
	private int selectedPage;

	/** SessionDataクラスからマッピング */
	public void mapFromSessionData(SessionData sessionData) {
		this.worksViewLimit = sessionData.getViewLimit();
		this.selectedPage = sessionData.getSelectedPage();
	}

	/** 一度に表示するページネーション範囲を計算 */
	public void calcViewArea(int totalWorks, int pagesViewLimit) {
		this.totalWorks = totalWorks;

		int rightLimit = (pagesViewLimit - 1) / 2;
		int leftLimit = pagesViewLimit / 2;

		if(this.totalWorks == 0) {
			this.startPage = 1;
			this.endPage = 1;
			return;
		}

		// 右にどれだけ表示できるか
		this.endPage = (this.totalWorks / this.worksViewLimit);
		if((this.totalWorks % this.worksViewLimit) != 0) {
			this.endPage += 1;
		}
		int rEnd = this.selectedPage + rightLimit;
		rEnd = this.endPage < rEnd ? this.endPage : rEnd;

		// 左にどれだけ表示できるか
		leftLimit += ((this.selectedPage + rightLimit) - rEnd);
		int lEnd = this.selectedPage - leftLimit;
		lEnd = lEnd < 1 ? 1 : lEnd;

		// もう一度右に表示できる数を調べる
		rightLimit += (lEnd - (this.selectedPage - leftLimit));
		rEnd = this.selectedPage + rightLimit;
		rEnd = this.endPage < rEnd ? this.endPage : rEnd;

		this.startPage = lEnd;
		this.endPage = rEnd;
	}
	/** 現在のページに作業が何件目まで表示しているかを取得 */
	public int getTotalWorksNowPage() {
		int out = this.selectedPage * worksViewLimit;
		return totalWorks < out ? totalWorks : out;
	}
}
