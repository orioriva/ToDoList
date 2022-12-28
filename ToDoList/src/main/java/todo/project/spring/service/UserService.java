package todo.project.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import todo.project.spring.form.UpdateUserForm;
import todo.project.spring.mapper.UserMapper;
import todo.project.spring.model.MUser;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;

	/** ユーザーを１名検索 */
	public MUser findUserOne(MUser user) {
		return mapper.findUserOne(user);
	}

	/** ユーザーを１名検索(ユーザー名) */
	public MUser findUserOneName(String name) {
		return mapper.findUserOneName(name);
	}

	/** ユーザーを１名検索(ID) */
	public MUser findUserOneId(int id) {
		return mapper.findUserOneId(id);
	}

	/** ユーザー1件の全ての情報を取得 */
	public MUser getUserOneAllStatus(int id) {
		return mapper.getUserOneAllStatus(id);
	}

	/** ユーザー一覧を取得 */
	public List<MUser> findUserAll(){
		return mapper.findUserAll();
	}

	/** 検索値を含むユーザー一覧を取得 */
	public List<MUser> searchUserAll(String order){
		return mapper.searchUserAll(order);
	}

	/** 一致するユーザー名の数を返す */
	public int countUserName(String userName) {
		return mapper.countUserName(userName);
	}

	/** ユーザー新規登録 */
	public void addUser(MUser user) {
		mapper.addUser(user);
	}

	/** ユーザーの登録内容を更新する */
	public void updateUser(MUser user) {
		mapper.updateUser(user);
	}

	/** ユーザー削除 */
	public void deleteUser(int id) {
		mapper.deleteUser(id);
	}

	/** ユーザーが存在するかチェック */
	public boolean isUserExists(int id) {
		MUser user = mapper.findUserOneId(id);
		return (user != null);
	}

	/** MUserクラスからInputUserFormクラスへのマッピング */
	public UpdateUserForm mapInputUserForm(UpdateUserForm form, MUser user) {
		form.setId(user.getId());
		form.setName(user.getName());
		form.setAdmin(user.isAdmin());

		return form;
	}
}
