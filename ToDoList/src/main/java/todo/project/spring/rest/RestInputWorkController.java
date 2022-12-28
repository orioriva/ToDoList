package todo.project.spring.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import todo.project.spring.form.InputWorkForm;
import todo.project.spring.model.MWork;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.ErrorCheckService;
import todo.project.spring.service.WorkService;

@RestController
public class RestInputWorkController {
	@Autowired
	private ErrorCheckService errorCheckService;

	@Autowired
	private SessionData sessionData;

	@Autowired
	private WorkService workService;

	/** 作業入力フォームのエラーチェック処理 */
	private Map<String, String> errorCherk(InputWorkForm form,
			BindingResult bindingResult)
	{
		Map<String, String> errors = new HashMap<>();

		// バリデーションエラーチェック
		errors = errorCheckService.getValidError(bindingResult, errors);
		// 担当者がデータベースに存在するかチェック
		errors = errorCheckService.getNotFoundUserError(form.getManagerName(), "managerName", errors);

		// エラーが見つかった場合
		if(!errors.isEmpty()) {
			// エラーの表示順番変更
			String[] order = {"workName","managerName","deadline"};
			errors = errorCheckService.sortErrorMap(errors, order);
		}

		return errors;
	}

	/** 作業登録 */
	@PostMapping("/inputWork/register")
	public RestResult postInputWork(Model model,
			@Validated InputWorkForm form,
			BindingResult bindingResult)
	{
		// エラーチェック
		Map<String, String> errors = errorCherk(form, bindingResult);
		if(!errors.isEmpty()) {
			return new RestResult(90, errors);
		}

		// 入力内容のマッピング
		MWork work = workService.mapMWork(form);
		work.setRegistrationDate(new Date());	// 登録日に今日の日付を代入
		work.setUserId(sessionData.getLoginUser().getId());

		// 作業追加処理
		workService.addWorkOne(work);

		return new RestResult(0, errors);
	}

	/** 作業更新 */
	@PutMapping("/inputWork/update")
	public RestResult putInputWork(Model model,
			@Validated InputWorkForm form,
			BindingResult bindingResult)
	{
		// 作業が既に削除されていないか？
		int workId = sessionData.getSelectedWork().getId();
		if(workService.isDeleted(workId)) {
			return new RestResult(404, null);
		}

		// エラーチェック
		Map<String, String> errors = errorCherk(form, bindingResult);
		if(!errors.isEmpty()) {
			return new RestResult(90, errors);
		}

		// 入力内容のマッピング
		MWork work = workService.mapMWork(form);
		work.setId(workId);

		Date date = sessionData.getSelectedWork().getCompletionDate();
		if(date != null && form.isComplete()) {
			// 完了日は元々の方を優先
			work.setCompletionDate(date);
		}

		workService.updateWorkOne(work);
		sessionData.setSelectedWork(null);

		return new RestResult(0, errors);
	}

	/** 作業を完了にする */
	@PutMapping("/inputWork/complete")
	public RestResult completeInputWork(@RequestParam("workId")Integer workId)
	{
		// 作業が既に削除されていないか？
		if(workService.isDeleted(workId)) {
			return new RestResult(404, null);
		}

		// 既に完了になっていないか？
		MWork work = workService.findWorkOne(workId);
		if(work.getCompletionDate() != null) {
			return new RestResult(410, null);
		}

		workService.completeWorkOne(workId);

		return new RestResult(0, null);
	}

	/** 作業削除 */
	@DeleteMapping("/inputWork/delete")
	public RestResult deleteInputWork()
	{
		// 作業が既に削除されていないか？
		int workId = sessionData.getSelectedWork().getId();
		if(workService.isDeleted(workId)) {
			return new RestResult(404, null);
		}

		workService.deleteWorkOne(workId);
		sessionData.setSelectedWork(null);

		return new RestResult(0, null);
	}
}
