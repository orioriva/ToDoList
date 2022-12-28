package todo.project.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import todo.project.spring.model.MUser;

@Mapper
public interface UserMapper {
	public MUser findUserOne(MUser user);
	public MUser findUserOneName(String name);
	public MUser findUserOneId(int id);
	public MUser getUserOneAllStatus(int id);
	public int countUserName(String userName);
	public List<MUser> findUserAll();
	public List<MUser> searchUserAll(String order);
	public void updateUser(MUser user);
	public void addUser(MUser user);
	public void deleteUser(int id);
}
