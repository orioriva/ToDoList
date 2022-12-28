package todo.project.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import todo.project.spring.form.LoginForm;
import todo.project.spring.model.SessionData;

@Controller
public class LoginController {
	@Autowired
	private SessionData sessionData;

	@GetMapping("/")
	public String getIndex() {
		return "redirect:/login";
	}

	/** ログイン画面 */
	@GetMapping("/login")
	public String getLogin(Model model,
			@ModelAttribute LoginForm form) {
		if(sessionData.getLoginUser() != null) {
			return "redirect:/workList";
		}else {
			return "login";
		}
	}

	/** パスワードが入力されたら */
	@PostMapping("/login")
	public String postLogin(){
		if(sessionData.getLoginUser() == null) {
			return "login";
		}
		return "redirect:/workList";
	}
}
