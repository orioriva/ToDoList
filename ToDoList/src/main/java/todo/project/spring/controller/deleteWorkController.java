package todo.project.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import todo.project.spring.form.InputWorkForm;
import todo.project.spring.model.MWork;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.WorkService;

@Controller
public class deleteWorkController {
	@Autowired
	private WorkService workService;

	@Autowired
	private SessionData sessionData;

	@GetMapping("/deleteWork")
	public String getDeleteWork(Model model)
	{
		// 確認フォーム初期値登録
		MWork work = sessionData.getSelectedWork();
		InputWorkForm form = workService.mapInputWorkForm(work);
		model.addAttribute("inputWorkForm",form);

		model.addAttribute(sessionData);

		return "deleteWork";
	}

	@PostMapping("/deleteWork")
	public String postDeleteWork(@RequestParam("workId")Integer workId)
	{
		sessionData.setSelectedWork(null);

		// セッションスコープに選択された作業データを登録
		MWork work = workService.findWorkOne(workId);
		sessionData.setSelectedWork(work);

		return "redirect:/deleteWork";
	}
}
