package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.StudentCourse;


/**
 * 
 * @author 李宗豪
 *
 */
public interface StudentCourseDao {
	/**
	 * 根据课程id和学生id找添加关系
	 * @param courseID
	 * @param studentID
	 */
	public void add(int courseID,int studentID);
	
	public void del(int id);
	
	/**
	 * 根据课程id和学生id找删除关系
	 * @param courseID
	 * @param studentID
	 */
	public void del(int courseID,int studentID);
	
	public void update(StudentCourse sc);
	/**
	 * 根据选课关系id找到选课关系
	 * @param id
	 * @return
	 */
	public StudentCourse find(int id);
	/**
	 * 根据课程id和学生id找对应关系
	 * @param courseID
	 * @param studentID
	 * @return
	 */
	public StudentCourse find(int courseID,int studentID);
	
	/**
	 * 找所有的选课关系
	 * @return
	 */
	public List<StudentCourse> findAll();
	/**
	 * 只找某一个用户的选课信息
	 * @param id 为用户id，即studentID
	 * @return
	 */
	public List<StudentCourse> findAll(int id);
	/**
	 * 改信息
	 * @param courseID
	 * @param studentID
	 * @param studentGrade
	 * @param studnetAnswer
	 */
	public void update(int courseID, int studentID, int studentGrade, String studnetAnswer);
	/**
	 * 根据课程id找所有选课关系
	 * @param courseID
	 */
	public List<StudentCourse> findByCouresID(int courseID);
	/**
	 * 根据课程id找到选这门课的学生的数量
	 * @param courseID
	 * @return
	 */
	public int findCountByCourseID(int courseID);
}
