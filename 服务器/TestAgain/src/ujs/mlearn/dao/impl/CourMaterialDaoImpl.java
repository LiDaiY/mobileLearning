package ujs.mlearn.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import ujs.mlearn.dao.CourseMaterialDao;
import ujs.mlearn.db.DataSourceManager;
import ujs.mlearn.entity.CourseMaterial;

public class CourMaterialDaoImpl implements CourseMaterialDao {
	private QueryRunner runner = new QueryRunner(DataSourceManager.getDataSource());
	@Override
	public void addRes(CourseMaterial courseMaterial) {
		if (courseMaterial==null) {
			System.out.println("资源为空");
			return;
		}
		System.out.println("准备上传资源");
		String sql="insert into coursematerial (courseID,publishTime,resTitle,resUrl,teacherID,size) values (?,?,?,?,?,?)";
		Object[] params= {courseMaterial.getCourseID(),courseMaterial.getPublishTime(),courseMaterial.getResTitle(),
				courseMaterial.getResUrl(),courseMaterial.getTeacherID(),courseMaterial.getSize()};
		try {
			runner.update(sql, params);
			System.out.println("上传成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<CourseMaterial> findCourseMaterial(int courseID) {
		String sql = "select * from coursematerial where courseID=?";
		try {
			List<CourseMaterial> materials = runner.query(sql, new BeanListHandler<CourseMaterial>(CourseMaterial.class),courseID);
			for(CourseMaterial cMaterial:materials) {
				cMaterial.setPublishTime(cMaterial.getPublishTime().substring(0, 19));
			}
			return materials;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CourseMaterial findMaterialByID(int resID) {
		// TODO Auto-generated method stub
		String sql = "select * from coursematerial where resID=?";
		try {
			CourseMaterial material = runner.query(sql, new BeanHandler<CourseMaterial>(CourseMaterial.class),resID);
			if (material!=null) {
				material.setPublishTime(material.getPublishTime().substring(0, 19));
				return material;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
