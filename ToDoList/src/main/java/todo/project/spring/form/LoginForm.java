package todo.project.spring.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {
	@NotBlank
	private String userName;

	@NotBlank
	private String password;
}
