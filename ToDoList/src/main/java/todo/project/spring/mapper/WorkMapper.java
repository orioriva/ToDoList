package todo.project.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import todo.project.spring.model.MWork;

@Mapper
public interface WorkMapper {
	public int countWorkAll(int userId);
	public int countWorkSearch(int userId, String order);
	public List<MWork> findWorkAll(int userId);
	public List<MWork> findWorkLimit(int userId, int index, int limit);
	public List<MWork> searchWorkAll(int userId, String order);
	public List<MWork> searchWorkLimit(int userId, String order, int index, int limit);
	public void addWorkOne(MWork work);
	public void updateWorkOne(MWork work);
	public MWork findWorkOne(int id);
	public void completeWorkOne(int id);
	public void deleteWorkOne(int id);
	public boolean isDeleted(int id);
}
