package todo.project.spring.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoiseMapper {
	@Autowired
	UserService userService;

	public Map<String, Object> createUsersMap(){
		Map<String, Object> usersMap = new LinkedHashMap<>();
		return usersMap;
	}
}
