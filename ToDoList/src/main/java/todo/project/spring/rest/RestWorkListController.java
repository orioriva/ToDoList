package todo.project.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import todo.project.spring.model.SessionData;
import todo.project.spring.service.UserService;

@RestController
public class RestWorkListController {
	@Autowired
	private SessionData sessionData;

	@Autowired
	private UserService userService;

	/** 表示件数が変更された時 */
	@PostMapping("/workList/changeViewLimit")
	public String postRestWorkListChangeViewLimit(
			@RequestParam("limitSelect")Integer limitSelect)
	{
		sessionData.setViewLimit(limitSelect);
		sessionData.initSelectedPage();

		return limitSelect.toString();
	}

	/** 表示ページが変更された時 */
	@PostMapping("/workList/changeViewPage")
	public String postWorkList(
			@RequestParam("page")Integer page)
	{
		sessionData.setSelectedPage(page);

		return page.toString();
	}
}
