package todo.project.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import todo.project.spring.model.MUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// ユーザー情報取得
		MUser loginUser = userService.findUserOneName(username);

		if(loginUser == null) {
			throw new UsernameNotFoundException("user not found");
		}

		// 権限List
		String role = loginUser.isAdmin() ? "ADMIN" : "COMMON";
		GrantedAuthority auth = new SimpleGrantedAuthority(role);
		List<GrantedAuthority> authList = new ArrayList<>();
		authList.add(auth);

		// UserDetails生成
		UserDetails userDetails = (UserDetails)new User(
				loginUser.getName(),
				loginUser.getPassword(),
				authList);

		return userDetails;
	}

}
