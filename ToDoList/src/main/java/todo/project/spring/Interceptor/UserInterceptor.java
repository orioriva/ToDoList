package todo.project.spring.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import todo.project.spring.model.SessionData;

@Component
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	SessionData sessionData;

	@Override
	public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {
		//コントローラー前に行いたい共通処理を書く。
		//System.out.println("intercepter通過");
		if(sessionData.getLoginUser() == null) {
			response.sendRedirect("/login");
			return false;
		}

		return true;
		//true: コントローラへ。false: コントローラーへ行かせない。
	}

	/*
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		//コントローラー後に行いたい共通処理を書く。

		//if(modelAndView != null)
		//	modelAndView.addObject(sessionData);
	}*/

	/*
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		@Nullable Exception ex) throws Exception {
		//リクエストの後に行いたい共通処理を書く。
	}*/
}
