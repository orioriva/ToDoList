package todo.project.spring.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UpdateUserForm {
	private Integer id;

	@NotBlank
	private String name;

	private boolean passwordChange = false;	// パスワードを変更するか

	@NotBlank
	@Length(max=100)
	private String nowPassword;

	@NotBlank
	@Length(max=100)
	private String newPassword;

	@NotBlank
	@Length(max=100)
	private String newPasswordConfirm;

	private boolean admin;
}
