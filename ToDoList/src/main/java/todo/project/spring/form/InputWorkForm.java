package todo.project.spring.form;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class InputWorkForm {
	@NotBlank
	private String workName;	// 項目名

	private String managerName;// 担当者名

	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date deadline;		// 期限

	private boolean complete;	// 既に完了しているかどうか
}
