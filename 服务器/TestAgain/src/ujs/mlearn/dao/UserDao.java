package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Student;

public interface UserDao {
	public void add(Student user);
	public void del(int id);
	public void update(Student user);
	public Student find(String email);
	public Student login(String email,String pwd);
	public List<Student> findAll();
	public Student findById(int userid);
	public void uduser(String attribute,String value,int userid);
}
