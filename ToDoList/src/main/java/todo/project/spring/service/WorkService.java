package todo.project.spring.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import todo.project.spring.form.InputWorkForm;
import todo.project.spring.mapper.WorkMapper;
import todo.project.spring.model.MWork;

@Service
public class WorkService {
	@Autowired
	private WorkMapper mapper;

	/** ユーザーIDと紐づいた作業数をカウント */
	public int countWorkAll(int userId) {
		return mapper.countWorkAll(userId);
	}

	/**  検索値を含む作業をカウント */
	public int countWorkSearch(int userId, String order) {
		return mapper.countWorkSearch(userId, order);
	}

	/** ユーザーIDから紐づけされた作業を取得 */
	public List<MWork> findWorkAll(int userId){
		return mapper.findWorkAll(userId);
	}

	/** ユーザーIDから紐づけされた作業を取得（表示数制限） */
	public List<MWork> findWorkLimit(int userId, int index, int limit){
		return mapper.findWorkLimit(userId, index, limit);
	}

	/** 検索値を含む作業を取得 */
	public List<MWork> searchWorkAll(int userId, String order){
		return mapper.searchWorkAll(userId,order);
	}

	/** 検索値を含む作業を取得（表示数制限） */
	public List<MWork> searchWorkLimit(int userId, String order, int index, int limit){
		return mapper.searchWorkLimit(userId, order, index, limit);
	}

	/** 作業を追加 */
	public void addWorkOne(MWork work) {
		mapper.addWorkOne(work);
	}

	/** 作業内容を更新 */
	public void updateWorkOne(MWork work) {
		mapper.updateWorkOne(work);
	}

	/** 作業IDから作業を１件取得 */
	public MWork findWorkOne(int id) {
		return mapper.findWorkOne(id);
	}

	/** 作業IDと一致する作業を完了にする */
	public void completeWorkOne(int id) {
		mapper.completeWorkOne(id);
	}

	/** 作業IDと一致する作業のdelete_flgをTrueにする（一覧に表示されなくする） */
	public void deleteWorkOne(int id) {
		mapper.deleteWorkOne(id);
	}

	/** その作業が既に削除しているか調べる */
	public boolean isDeleted(int id) {
		return mapper.isDeleted(id);
	}

	/** InputWorkFormクラスからMWorkクラスへのマッピング */
	public MWork mapMWork(InputWorkForm form) {
		// 登録情報の設定
		MWork work = new MWork();
		work.setWorkName(form.getWorkName());
		work.setManager(form.getManagerName());
		work.setDeadline(form.getDeadline());

		if(form.isComplete()) {
			// 現在時刻を入力
			Date nowDate = new Date();
			work.setCompletionDate(nowDate);
		}else {
			work.setCompletionDate(null);
		}

		return work;
	}

	/** MWorkクラスからInputWorkFormクラスへのマッピング */
	public InputWorkForm mapInputWorkForm(MWork work) {
		InputWorkForm form = new InputWorkForm();
		return mapInputWorkForm(form, work);
	}
	public InputWorkForm mapInputWorkForm(InputWorkForm form, MWork work) {
		form.setWorkName(work.getWorkName());
		form.setDeadline(work.getDeadline());
		form.setManagerName(work.getManager());
		form.setComplete(work.getCompletionDate() != null ? true : false);

		return form;
	}
}
