package todo.project.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import todo.project.spring.model.SessionData;

@Controller
@SessionAttributes("scopedTarget.sessionData")
public class LogoutController {
	@Autowired
	SessionData sessionData;

	@GetMapping({"/logout/complete","/logout"})
	public String getLogout(SessionStatus sessionStatus) {
		if(!sessionStatus.isComplete())
			sessionStatus.setComplete();
		return "redirect:/login";
	}

	@PostMapping("/logout")
	public String postLogout(SessionStatus sessionStatus) {
		if(!sessionStatus.isComplete())
			sessionStatus.setComplete();
		return "redirect:/login";
	}
}
