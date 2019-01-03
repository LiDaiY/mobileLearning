package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Teacher;


public interface TeacherDao {
	public void add(Teacher teacher);
	public void del(int id);
	public void update(Teacher teacher);
	public Teacher find(String email);
	public Teacher login(String email,String pwd);
	public List<Teacher> findAll();
	public Teacher findById(int userid);
	public void uduser(String attribute,String value,int userid);

	
}
