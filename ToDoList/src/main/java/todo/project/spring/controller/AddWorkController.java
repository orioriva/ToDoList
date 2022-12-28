package todo.project.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import todo.project.spring.form.InputWorkForm;
import todo.project.spring.model.MUser;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.UserService;

@Controller
public class AddWorkController {
	@Autowired
	private UserService userService;

	@Autowired
	private SessionData sessionData;

	@GetMapping("/addWork")
	public String getAddWork(Model model,
			@ModelAttribute InputWorkForm form)
	{
		// 担当者選択肢用のユーザー一覧取得
		List<MUser> userList = userService.findUserAll();
		model.addAttribute("userList",userList);

		model.addAttribute(sessionData);
		return "addWork";
	}
}
