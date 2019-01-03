package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Homework;

public interface HomeworkDao {
	public void addHomework(Homework homework);
	
	public List<Homework> findHwByCourseID(int courseID);
	
	public void updateHomework(Homework homework);
	
	public void deleteHomework(int hwID);

	public Homework findHwByHwID(int hwID);
	
}
