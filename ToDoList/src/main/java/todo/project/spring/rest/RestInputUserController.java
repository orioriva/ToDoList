package todo.project.spring.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import todo.project.spring.form.AddUserForm;
import todo.project.spring.form.UpdateUserForm;
import todo.project.spring.model.MUser;
import todo.project.spring.model.SessionData;
import todo.project.spring.service.ErrorCheckService;
import todo.project.spring.service.UserService;

@RestController
public class RestInputUserController {
	@Autowired
	private ErrorCheckService errorCheckService;

	@Autowired
	private SessionData sessionData;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	/** ユーザー登録 */
	@PostMapping("/admin/rest/addUser")
	public RestResult postInputUser(Model model,
			@Validated AddUserForm form,
			BindingResult bindingResult)
	{
		Map<String, String> errors = new HashMap<>();

		// エラーチェック
		errors = errorCheckService.getValidError(bindingResult, errors);
		errors = errorCheckService.getPasswordError(form, "passwordConfirm", errors);
		errors = errorCheckService.getDuplicatedNameError(form.getName(), "name", errors);

		// エラーが見つかった場合
		if(!errors.isEmpty()) {
			// エラーの表示順番変更
			String[] order = {"name","password","passwordConfirm"};
			errors = errorCheckService.sortErrorMap(errors, order);
			return new RestResult(90, errors);
		}

		// 入力内容で情報登録
		MUser user = new MUser();
		user.setName(form.getName());
		user.setPassword(encoder.encode(form.getPassword()));
		user.setAdmin(form.isAdmin());
		userService.addUser(user);

		return new RestResult(0, null);
	}

	/** ユーザー情報更新 */
	@PutMapping("/admin/rest/updateUser")
	public RestResult putInputUser(Model model,
			@Validated UpdateUserForm form,
			BindingResult bindingResult)
	{
		Map<String, String> errors = new HashMap<>();
		// パスワードも含めた全ての情報取得
		MUser user = userService.getUserOneAllStatus(sessionData.getSelectedUser().getId());

		// バリデーションエラーチェック
		errors = errorCheckService.getValidError(bindingResult, errors);
		// パスワード変更しないならパスワード関係のエラーを削除
		if(!form.isPasswordChange()) {
			errors.remove("nowPassword");
			errors.remove("newPassword");
			errors.remove("newPasswordConfirm");
		}else {
			// パスワードに不備が無いか？
			errors = errorCheckService.getPasswordError(
					form,
					user.getPassword(),
					"nowPassword",
					"newPasswordConfirm",
					errors);

			user.setPassword(encoder.encode(form.getNewPassword()));
		}
		// 元々のユーザー名から変更されている場合はユーザー名が重複していないかチェックする
		if(!sessionData.getSelectedUser().getName().equals(form.getName())) {
			errors = errorCheckService.getDuplicatedNameError(form.getName(), "name", errors);
		}

		// エラーが見つかった場合
		if(!errors.isEmpty()) {
			// エラーの表示順番変更
			String[] order = {"name","nowPassword","newPassword","newPasswordConfirm"};
			errors = errorCheckService.sortErrorMap(errors, order);
			return new RestResult(90, errors);
		}

		// 入力内容で情報更新
		user.setName(form.getName());
		user.setAdmin(form.isAdmin());
		userService.updateUser(user);

		// 名前変わっているかも知れないので情報取り直す
		int myUserId = sessionData.getLoginUser().getId();
		if(myUserId == user.getId()) {
			sessionData.setLoginUser(userService.findUserOneId(myUserId));
		}

		return new RestResult(0, null);
	}

	/** パスワードリセット */
	@PutMapping("/admin/rest/resetPassword")
	public RestResult putResetPassword() {
		// パスワードも含めた全ての情報取得
		MUser user = userService.getUserOneAllStatus(sessionData.getSelectedUser().getId());

		// ランダムなパスワードを登録する
		String randomPass = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(encoder.encode(randomPass));
		userService.updateUser(user);

		Map<String, String> errors = new HashMap<>();
		errors.put("msg", randomPass);
		return new RestResult(10, errors);
	}

	/** ユーザー削除 */
	@DeleteMapping("/admin/rest/deleteUser")
	public RestResult deleteInputUser() {
		int userId = sessionData.getSelectedUser().getId();

		// 自分を削除しようとした時
		if(sessionData.getLoginUser().getId() == userId) {
			return new RestResult(999, null);
		}
		// 該当ユーザーが存在しない時
		if(!userService.isUserExists(userId)) {
			return new RestResult(404, null);
		}

		// ユーザー削除
		userService.deleteUser(userId);

		return new RestResult(0, null);
	}
}
