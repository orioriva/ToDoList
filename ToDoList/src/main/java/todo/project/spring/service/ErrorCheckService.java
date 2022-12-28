package todo.project.spring.service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import todo.project.spring.form.AddUserForm;
import todo.project.spring.form.UpdateUserForm;

@Service
public class ErrorCheckService {
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	/** バリデーションエラーがあるかのチェック */
	public Map<String,String> getValidError(BindingResult bindingResult, Map<String, String> errors) {
		// エラーチェック
		if(bindingResult.hasErrors()) {
			// 入力エラーが見つかった場合

			// エラーメッセージ取得
			for(FieldError error: bindingResult.getFieldErrors()) {
				String message = messageSource.getMessage(error, Locale.JAPAN);
				errors.put(error.getField(), message);
			}

			// エラー結果の返却
			return errors;
		}

		return errors;
	}

	/** 同じユーザー名を持つユーザーが存在しているかチェック */
	public Map<String, String> getNotFoundUserError(String userName, String errorKey, Map<String, String> errors) {
		int count = userService.countUserName(userName);
		if(count == 0) {
			// エラー結果の追加
			String message = messageSource.getMessage("NotFoundUser", null, Locale.JAPAN);
			errors.put(errorKey, message);
		}
		return errors;
	}

	/** ユーザー名が重複していないかチェック */
	public Map<String, String> getDuplicatedNameError(String userName, String errorKey, Map<String, String> errors) {
		int count = userService.countUserName(userName);
		if(count > 0) {
			// エラー結果の追加
			String message = messageSource.getMessage("DuplicatedName", null, Locale.JAPAN);
			errors.put(errorKey, message);
		}
		return errors;
	}

	/** パスワードが有効かチェック（新規登録画面） */
	public Map<String, String> getPasswordError(AddUserForm form, String errorKey, Map<String, String> errors){
		// 新しいパスワード２つに同じ文字が入力されているか
		if(!form.getPassword().equals(form.getPasswordConfirm())) {
			String message = messageSource.getMessage("NotMatchNewPassword", null, Locale.JAPAN);
			errors.put(errorKey, message);
		}

		return errors;
	}
	/** パスワードが有効かチェック（ユーザー情報更新画面） */
	public Map<String, String> getPasswordError(
			UpdateUserForm form, String userPassword, String errorKeyNowPass, String errorKeyConfirm, Map<String, String> errors
			)
	{
		// 現在のパスワードと一致するか
		if(!encoder.matches(form.getNowPassword(), userPassword)) {
			String message = messageSource.getMessage("NotMatchNowPassword", null, Locale.JAPAN);
			errors.put(errorKeyNowPass, message);
		}

		// 新しいパスワード２つに同じ文字が入力されているか
		if(!form.getNewPassword().equals(form.getNewPasswordConfirm())) {
			String message = messageSource.getMessage("NotMatchNewPassword", null, Locale.JAPAN);
			errors.put(errorKeyConfirm, message);
		}

		return errors;
	}

	/** エラーの並び順を指定された順番に変更 */
	public Map<String, String> sortErrorMap(Map<String, String> errors, String[] order){
		Map<String, String> sortErrors = new LinkedHashMap<>();

		// 指定順にmapに追加
		for(String key: order) {
			String value = errors.get(key);
			if(value != null) {
				sortErrors.put(key, value);
				errors.remove(key);
			}
		}
		// 指定以外にもまだ残っているなら追加する
		for(Iterator<String> itr = errors.keySet().iterator(); itr.hasNext();) {
			String key = itr.next();
			sortErrors.put(key, errors.get(key));
        }

		return sortErrors;
	}
}
