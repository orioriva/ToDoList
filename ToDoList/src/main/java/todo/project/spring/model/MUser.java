package todo.project.spring.model;

import lombok.Data;

@Data
public class MUser {
	private boolean admin;
	private Integer id;
	private String name;
	private String password;
}
