package todo.project.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import todo.project.spring.model.MUser;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.UserService;

@Controller
public class UserListController {

	@Autowired
	private SessionData sessionData;

	@Autowired
	private UserService userService;

	@GetMapping("/admin/userList")
	public String getUserList(Model model){
		sessionData.setSelectedUser(null);
		sessionData.setSearchTextUser(null);

		// ユーザーリスト取得
		List<MUser> userList = userService.findUserAll();
		model.addAttribute("userList",userList);

		sessionData.setReturnPage("/admin/userList");
		model.addAttribute(sessionData);
		return "admin/userList";
	}
}
