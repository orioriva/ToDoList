package todo.project.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import todo.project.spring.form.UpdateUserForm;
import todo.project.spring.model.MUser;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.UserService;

@Controller
public class UpdateUserController {
	@Autowired
	private SessionData sessionData;

	@Autowired
	private UserService userService;

	/*
	 *  一般ユーザー用 Get Post
	 */

	@GetMapping("/updateUser")
	public String getUpdateUser(Model model,
			@ModelAttribute UpdateUserForm form)
	{
		if(sessionData.getLoginUser().getId() != sessionData.getSelectedUser().getId()) {
			return "redirect:/login";
		}

		getUpdateUserAdmin(model,form);

		return "updateUser";
	}

	@PostMapping("/updateUser")
	public String postUpdateUser()
	{
		MUser user = userService.findUserOneId(sessionData.getLoginUser().getId());
		sessionData.setSelectedUser(user);
		return "redirect:/updateUser";
	}

	/*
	 *  管理者用 Get Post
	 */

	@GetMapping("/admin/updateUser")
	public String getUpdateUserAdmin(Model model,
			@ModelAttribute UpdateUserForm form)
	{
		form = userService.mapInputUserForm(form, sessionData.getSelectedUser());
		model.addAttribute(sessionData);
		return "updateUser";
	}

	@PostMapping("/admin/updateUser")
	public String postUpdateUserAdmin(@RequestParam("userId")Integer userId)
	{
		MUser user = userService.findUserOneId(userId);
		sessionData.setSelectedUser(user);
		return "redirect:/admin/updateUser";
	}
}
