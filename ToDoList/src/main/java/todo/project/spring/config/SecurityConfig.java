package todo.project.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder encoder;

	/** セキュリティの対象外を設定 */
	@Override
	public void configure(WebSecurity web) throws Exception{
		web
			.ignoring()
				.antMatchers("/webjars/**")	// bootstrap
				.antMatchers("/css/**")
				.antMatchers("/js/**")
				;
	}

	/** セキュリティの各種設定 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		// ログイン不要ページの設定
		http
			.authorizeRequests()
				.antMatchers("/**").permitAll()	// 直リンクOK
				.anyRequest().authenticated()		// それ以外は直リンクNG
				;

		// ログイン処理
		http
			.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.failureUrl("/login?error")
				.usernameParameter("userName")	// ログインページのユーザー名のname
				.passwordParameter("password")	// ログインページのパスワードのname
				.defaultSuccessUrl("/workList",true)	// 成功後の遷移先
				;

		// ログアウト処理
		http
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutUrl("/logout")
				.logoutSuccessUrl("/logout/complete")
				;

		// CSRF対策
		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		// インメモリ認証
		/*auth
			.inMemoryAuthentication()
				.withUser("テスト")
					.password(encoder.encode("test"))
					.roles("ADMIN")
					;*/

		// ユーザーデータで認証
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder);
	}
}
