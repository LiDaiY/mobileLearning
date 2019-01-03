package ujs.mlearn.dao;

import java.util.List;

import ujs.mlearn.entity.CourseMaterial;

public interface CourseMaterialDao {
	public void addRes(CourseMaterial courseMaterial);
	/**
	 * 根据课程id查找本课程所有的资源
	 * @param courseID
	 * @return
	 */
	public List<CourseMaterial> findCourseMaterial(int courseID);
	/**
	 * 通过资源id获取资源
	 * @param resID
	 */
	public CourseMaterial findMaterialByID(int resID);
}
