package ujs.mlearn.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.Utils.CommonUtil;
import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.StudentCourseDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.StudentCourseDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.SentMessage;
import ujs.mlearn.entity.StudentCourse;


public class CourseListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CourseListServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String op  = request.getParameter("operation");
		System.out.println(op);
		if(op.equals("findAllCourse")){//显示所有课程
			findAllcourse(request,response);
		}else if(op.equals("findMyCourse")) {//显示用户选的课
			findMyCourse(request,response);
		}else if(op.equals("selectCourse")){//用户选课
			selectCourse(request,response);
		}else if(op.equals("cancelCourse")){//用户退订课程
			cancelCourse(request,response);
		}else if (op.equals("checkSelect")) {//查看是否选课了
			checkSelect(request,response);
		}
		
	}


	private void checkSelect(HttpServletRequest request, HttpServletResponse response) {
		SentMessage message;
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		int studentID=Integer.parseInt(request.getParameter("studentID"));
		StudentCourseDao studentCourseDao=new StudentCourseDaoImpl();
		StudentCourse sCourse=studentCourseDao.find(courseID, studentID);
		if (sCourse==null) {//为空，说明没选
			 message = new SentMessage(2, "0");//2表示查询,0为空
			 CommonUtil.renderJson(response, message);	
		}else {
			 message = new SentMessage(2, "1");//2表示查询,1为有值
			 CommonUtil.renderJson(response, message);	
		}
	}


	private void cancelCourse(HttpServletRequest request, HttpServletResponse response) {
		SentMessage message;
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		int studentID=Integer.parseInt(request.getParameter("studentID"));
		StudentCourseDao studentCourseDao=new StudentCourseDaoImpl();
		StudentCourse sCourse=studentCourseDao.find(courseID, studentID);
		if (sCourse==null) {//为空，说明没选，故无法退订
			 message = new SentMessage(0, "退订失败");
			 CommonUtil.renderJson(response, message);
		}else {//为空，说明没选，故选课
			studentCourseDao.del(courseID, studentID);
			 message = new SentMessage(1, "退订成功");
			 CommonUtil.renderJson(response,message);
		}
	}


	private void selectCourse(HttpServletRequest request, HttpServletResponse response) {
		SentMessage message;
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		int studentID=Integer.parseInt(request.getParameter("studentID"));
		StudentCourseDao studentCourseDao=new StudentCourseDaoImpl();
		StudentCourse sCourse=studentCourseDao.find(courseID, studentID);
		if (sCourse!=null) {//不为空，说明已经选了
			 message = new SentMessage(0, "选课失败：此课已选");
			 CommonUtil.renderJson(response, message);
		}else {//为空，说明没选，故选课
			studentCourseDao.add(courseID, studentID);
			 message = new SentMessage(1, "选课成功");
			 CommonUtil.renderJson(response,message);
		}
	}


	private void findMyCourse(HttpServletRequest request, HttpServletResponse response) {
		StudentCourseDao studentCourseDao=new StudentCourseDaoImpl();
		int id  = Integer.parseInt(request.getParameter("studentID"));

		
		List<StudentCourse> scRelation=(List<StudentCourse>) studentCourseDao.findAll(id);//取出学生的选课关系
		

		List<Course> courses=new ArrayList<Course>();
		CourseDao courseDao=new CourseDaoImpl();
		for(StudentCourse sCourse:scRelation) {
			int courseID=sCourse.getCourseID();//取出课程id
			Course course=courseDao.find(courseID);
			courses.add(course);
		}
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("courses", courses);
		CommonUtil.renderJson(response, map);
	}


	private void findAllcourse(HttpServletRequest request, HttpServletResponse response) {
		CourseDao courseDao=new CourseDaoImpl();
		List<Course> courses=courseDao.findAll();
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("courses", courses);
		CommonUtil.renderJson(response, map);
	}

}
