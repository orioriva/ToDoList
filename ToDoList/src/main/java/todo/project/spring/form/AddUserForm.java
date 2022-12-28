package todo.project.spring.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class AddUserForm {
	@NotBlank
	private String name;

	@NotBlank
	@Length(max=100)
	private String password;

	@NotBlank
	@Length(max=100)
	private String passwordConfirm;

	private boolean admin;
}
