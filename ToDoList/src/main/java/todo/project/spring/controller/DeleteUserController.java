package todo.project.spring.controller;

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
public class DeleteUserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SessionData sessionData;

	@GetMapping("/admin/deleteUser")
	public String getDeleteUser(Model model) {
		model.addAttribute(sessionData);
		return "admin/deleteUser";
	}

	@PostMapping("/admin/deleteUser")
	public String postDeleteUser(@RequestParam("userId")Integer userId) {
		MUser user = userService.findUserOneId(userId);
		sessionData.setSelectedUser(user);
		return "redirect:/admin/deleteUser";
	}
}
