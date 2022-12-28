package todo.project.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import todo.project.spring.model.MUser;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.UserService;

@Controller
public class SearchUserResultController {
	@Autowired
	private UserService userService;

	@Autowired
	private SessionData sessionData;

	@GetMapping("/admin/searchUserResult")
	public String getSearchUserResult(Model model)
	{
		sessionData.setReturnPage("/admin/searchUserResult");

		List<MUser> userList = userService.searchUserAll(sessionData.getSearchTextUser());
		model.addAttribute("userList",userList);

		model.addAttribute(sessionData);
		return "admin/searchUserResult";
	}

	/** 検索ボタンが押されたとき */
	@PostMapping("/admin/searchUserResult")
	public String postSearchUserResult(Model model,@RequestParam("searchText")String searchText)
	{
		sessionData.setSearchTextUser(searchText);

		return "redirect:/admin/searchUserResult";
	}
}
