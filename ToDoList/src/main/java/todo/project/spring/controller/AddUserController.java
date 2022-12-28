package todo.project.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import todo.project.spring.form.AddUserForm;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.UserService;

@Controller
public class AddUserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SessionData sessionData;

	@GetMapping("/admin/addUser")
	private String getAddUser(Model model,
			@ModelAttribute AddUserForm form) {
		model.addAttribute(sessionData);
		return "admin/addUser";
	}
}
