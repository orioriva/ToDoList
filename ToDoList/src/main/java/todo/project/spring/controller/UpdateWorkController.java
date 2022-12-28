package todo.project.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import todo.project.spring.form.InputWorkForm;
import todo.project.spring.model.MUser;
import todo.project.spring.model.MWork;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.UserService;
import todo.project.spring.service.WorkService;

@Controller
public class UpdateWorkController {
	@Autowired
	private UserService userService;

	@Autowired
	private WorkService workService;

	@Autowired
	private SessionData sessionData;

	@GetMapping("/updateWork")
	public String getUpdateWork(Model model,
			@ModelAttribute InputWorkForm form)
	{
		// 担当者選択肢用のユーザー一覧取得
		List<MUser> userList = userService.findUserAll();
		model.addAttribute("userList",userList);

		// 入力フォーム初期値登録
		MWork work = sessionData.getSelectedWork();
		form = workService.mapInputWorkForm(form, work);

		model.addAttribute(sessionData);

		return "updateWork";
	}

	@PostMapping("/updateWork")
	public String postUpdateWork(@RequestParam("workId")Integer workId)
	{
		sessionData.setSelectedWork(null);

		// セッションスコープに選択された作業データを登録
		MWork work = workService.findWorkOne(workId);
		sessionData.setSelectedWork(work);

		return "redirect:/updateWork";
	}
}
