package todo.project.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import todo.project.spring.Interceptor.AdminInterceptor;
import todo.project.spring.Interceptor.UserInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Autowired
	private UserInterceptor userInterceptor;

	@Autowired
	private AdminInterceptor adminInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userInterceptor)
			.addPathPatterns("/*")
			.excludePathPatterns("/login")
			.excludePathPatterns("/login/rest")
			;

		// 管理者専用画面
		registry.addInterceptor(adminInterceptor)
			.addPathPatterns("/admin/*");
	}
}
