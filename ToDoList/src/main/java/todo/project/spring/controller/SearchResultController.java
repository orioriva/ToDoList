package todo.project.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import todo.project.spring.model.MWork;
import todo.project.spring.model.Pagination;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.WorkService;

@Controller
public class SearchResultController {
	@Autowired
	private SessionData sessionData;

	@Autowired
	private WorkService workService;

	@GetMapping("/searchResult")
	public String getSearchResult(Model model)
	{
		sessionData.setReturnPage("/searchResult");

		int userId = sessionData.getLoginUser().getId();
		String searchText = sessionData.getSearchText();

		// 作業の総数を取得
		int totalWorks = workService.countWorkSearch(userId, searchText);

		// ページネーションの設定
		Pagination pagination = new Pagination();
		pagination.mapFromSessionData(sessionData);
		pagination.calcViewArea(totalWorks, 10);
		model.addAttribute("pagination",pagination);

		int limit = sessionData.getViewLimit();
		int index = (sessionData.getSelectedPage() - 1) * limit;

		// 項目名と担当者名から検索値と部分一致する作業一覧を取得
		List<MWork> workList = workService.searchWorkLimit(
				sessionData.getLoginUser().getId(),
				sessionData.getSearchText(),
				index,
				limit);

		model.addAttribute("workList",workList);

		model.addAttribute(sessionData);

		return "/searchResult";
	}

	/** 検索ボタンが押されたとき */
	@PostMapping("/searchResult")
	public String postSearchResult(Model model,
			@RequestParam("searchText")String searchText)
	{
		sessionData.initSelectedPage();
		sessionData.setSearchText(searchText);

		return "redirect:/searchResult";
	}
}
