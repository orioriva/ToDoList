package todo.project.spring.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
//@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class SessionData implements Serializable{
	private final int DEFAULT_SELECTED_PAGE = 1;
	private final int DEFAULT_VIEW_LIMIT = 5;
	private final String DEFAULT_RETURN_PAGE = "/workList";

	private MUser loginUser;
	private MWork selectedWork;
	private MUser selectedUser;

	private String searchText;
	private String searchTextUser;
	private String returnPage = DEFAULT_RETURN_PAGE;

	// ページネーション関係
	private Integer selectedPage = DEFAULT_SELECTED_PAGE;	// 選択されているページ
	private Integer viewLimit = DEFAULT_VIEW_LIMIT;	// ページごとの最大表示作業数
	private Integer maxViewPage = 10;	// ページネーションの最大表示ページ数

	public void initSelectedPage() {
		this.selectedPage = DEFAULT_SELECTED_PAGE;
	}
	public void initViewLimit() {
		this.viewLimit = DEFAULT_VIEW_LIMIT;
	}
}
