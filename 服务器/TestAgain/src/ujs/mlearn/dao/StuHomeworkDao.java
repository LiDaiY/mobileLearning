package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.StuHomework;

public interface StuHomeworkDao {
	/**
	 * 通过作业id找到学生所提交的作业
	 * @param courseID
	 * @return
	 */
	public List<StuHomework> findStuHwByHwID(int hwID);
	/**
	 * 学生第一次上传作业
	 * @param sHomework
	 */
	public void upHomework(StuHomework sHomework);
	/**
	 * 学生更新作业
	 * @param sHomework
	 * @param shwID
	 */
	public void upHomework(StuHomework sHomework,int shwID);
	
	/**
	 * 通过提交的作业ID找到学生所提交的作业
	 * @param shwID
	 * @return
	 */
	public StuHomework findOneWork(int shwID);
	/**
	 * 通过作业id和学生id找到学生是否已经提交了作业，如果学生已经提交了作业，则修改数据，若果没有提交，则添加数据
	 * @param hwID
	 * @param studentID
	 */
	public StuHomework findWork(int hwID, int studentID);
}
