package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Course;


public interface CourseDao {
	/**
	 * 返回课程的封面路径
	 * @param course
	 * @return
	 */
	public String add(Course course);

	public void del(int id);
	public void update(Course course);
	/**
	 * 根据课程id取出课程
	 * @param id
	 */
	public Course find(int id);
	/**
	 * 根据老师id取出老师所教的课
	 * @param id
	 * @return
	 */
	public List<Course> findMyCourse(int teacherID);
	public List<Course> findAll();
	
	/**
	 * 根据课程名称和老师id取出课程
	 * @param courseName
	 * @param teacherName
	 * @return
	 */
	public Course findIdByName(String courseName,int teacherID); 

}
