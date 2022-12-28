package todo.project.spring.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import todo.project.spring.model.SessionData;

@Component
public class AdminInterceptor implements HandlerInterceptor{
	@Autowired
	SessionData sessionData;

	@Override
	public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {
		//コントローラー前に行いたい共通処理
		if(sessionData.getLoginUser() == null || !sessionData.getLoginUser().isAdmin()) {
			response.sendRedirect("/login");
			return false;
		}

		return true;
		//true: コントローラへ。false: コントローラーへ行かせない。
	}
}
