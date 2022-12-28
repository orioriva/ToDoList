package todo.project.spring.rest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import todo.project.spring.form.LoginForm;
import todo.project.spring.model.MUser;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.UserService;

@RestController
public class RestLoginController {
	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private SessionData sessionData;

	@PostMapping("/login/rest")
	public RestResult postRestLogin(Model model,
			@Validated LoginForm form,
			BindingResult bindingResult)
	{
		// エラーチェック
		if(bindingResult.hasErrors()) {
			// 入力エラーが見つかった場合
			Map<String, String> errors = new HashMap<>();

			// エラーメッセージ取得
			for(FieldError error: bindingResult.getFieldErrors()) {
				String message = messageSource.getMessage(error, Locale.JAPAN);
				errors.put(error.getField(), message);
			}

			// エラー結果の返却
			return new RestResult(90, errors);
		}

		// ユーザー名とパスワードが一致するかチェック
		MUser findUser = userService.findUserOneName(form.getUserName());
		if(findUser == null ||
			!encoder.matches(form.getPassword(), findUser.getPassword())
			)
		{
			// ユーザーが見つからなかった場合
			Map<String, String> errors = new HashMap<>();

			// エラー結果の返却
			String message = messageSource.getMessage("NotMatchNamePass", null, Locale.JAPAN);
			errors.put("none", message);
			return new RestResult(90, errors);
		}

		// セッションスコープにユーザーデータを保存
		findUser.setPassword(null);
		sessionData.setLoginUser(findUser);

		return new RestResult(0, null);
	}
}
