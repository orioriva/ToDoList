package todo.project.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import todo.project.spring.model.MWork;
import todo.project.spring.model.Pagination;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.WorkService;

@Controller
public class WorkListController {
	@Autowired
	SessionData sessionData;

	@Autowired
	WorkService workService;

	@GetMapping("/workList")
	public String getWorkList(Model model) {
		sessionData.setSearchText(null);

		// 他のページから戻ってきたのなら選択ページを初期化する
		String thisPage = "/workList";
		if(sessionData.getReturnPage() != null && !sessionData.getReturnPage().equals(thisPage)) {
			sessionData.initSelectedPage();
			sessionData.setReturnPage(thisPage);
		}

		int userId = sessionData.getLoginUser().getId();

		// 作業の総数を取得
		int totalWorks = workService.countWorkAll(userId);

		// ページネーションの設定
		Pagination pagination = new Pagination();
		pagination.mapFromSessionData(sessionData);
		pagination.calcViewArea(totalWorks, 10);
		model.addAttribute("pagination",pagination);

		// ユーザーIDから作業一覧を取得
		int limit = sessionData.getViewLimit();
		int index = (sessionData.getSelectedPage() - 1) * limit;

		List<MWork> workList = workService.findWorkLimit(userId, index, limit);
		model.addAttribute("workList",workList);

		model.addAttribute(sessionData);
		return "workList";
	}


}
