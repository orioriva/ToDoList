package todo.project.spring.model;

import java.util.Calendar;
import java.util.Date;

import lombok.Data;

@Data
public class MWork {
	private Integer id;
	private Integer userId;
	private String workName;
	private String manager;
	private Date registrationDate;
	private Date deadline;
	private Date completionDate;

	/** 期限が過ぎているか判定
	 * （未完了かつ今日が期限日の翌日以降ならTRUE） */
	public boolean isExpired() {
		if(this.completionDate != null)
			return false;

		// 現在の日付（日付のみ判定したいので時間は0をセット）
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);

		// 期限日（カレンダーインスタンスに変換。時間は元々0になっている）
		Calendar cDeadline = Calendar.getInstance();
		cDeadline.setTime(this.deadline);

		return (today.compareTo(cDeadline) > 0);
	}
}
