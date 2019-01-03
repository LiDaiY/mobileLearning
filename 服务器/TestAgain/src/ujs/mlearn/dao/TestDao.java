package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.Test;

public interface TestDao {
	
	public void addtest(Test test);
	
	public void deltest(int testid);
	
//	public void updatetest(Test test);
	
	/**
	 * 找到某一门课的测试
	 * @param courseID
	 * @return
	 */
	public List<Test> findCourseTest(int courseID);
	
	/**
	 * 找到每一门课的测试
	 * @return
	 */
	public List<Test> findAllTest();
	
	/**
	 * 根据题目id找到题目，用于修改或者删除题目
	 * @return
	 */
	public Test findTest(int testID);
	/**
	 * 修改测试
	 * @param test
	 */
	public void updateTest(Test test);
}
