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
import ujs.mlearn.dao.StudentCourseDao;
import ujs.mlearn.dao.TestDao;
import ujs.mlearn.dao.impl.StudentCourseDaoImpl;
import ujs.mlearn.dao.impl.TestDaoImpl;
import ujs.mlearn.entity.SentMessage;
import ujs.mlearn.entity.StudentCourse;
import ujs.mlearn.entity.Test;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String op  = request.getParameter("operation");
		System.out.println(op);
		if(op.equals("findCourseTestScore")){//显示课程和分数
			findCourseTestScore(request,response);
		}else if(op.equals("findTestAccordingCourseID")) {
			findTestAccordingCourseID(request,response);
		}else if (op.equals("subScore")) {//提交回答
			subScore(request,response);
		}
	}

	private void subScore(HttpServletRequest request, HttpServletResponse response) {
		int courseID=Integer.parseInt((request.getParameter("courseID")));
		System.out.println("courseID:"+courseID);
		int studentID=Integer.parseInt((request.getParameter("studentID")));
		System.out.println("studentID:"+studentID);
		int studentGrade=Integer.parseInt((request.getParameter("studentGrade")));
		System.out.println("studentGrade:"+studentGrade);

		String studentAnswer=request.getParameter("studentAnswer");
		System.out.println("哈哈"+courseID+studentID+studentGrade+studentAnswer);
		StudentCourseDao scCourseDao =new StudentCourseDaoImpl();
		scCourseDao.update(courseID,studentID,studentGrade,studentAnswer);
		SentMessage message;
		message = new SentMessage(1, "提交成功");
		CommonUtil.renderJson(response,message);
	}

	private void findTestAccordingCourseID(HttpServletRequest request, HttpServletResponse response) {
		int courseID=Integer.parseInt((request.getParameter("courseID")));
		TestDao testdao = new TestDaoImpl();
		List<Test> tests = testdao.findCourseTest(courseID);
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("testList", tests);
		CommonUtil.renderJson(response, map);
	}

	private void findCourseTestScore(HttpServletRequest request, HttpServletResponse response) {
		int studentID=Integer.parseInt(request.getParameter("studentID"));
		StudentCourseDao sc=new StudentCourseDaoImpl();
		List<StudentCourse> sCourses=new ArrayList<>();
		sCourses=sc.findAll(studentID);
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("relationList", sCourses);
		CommonUtil.renderJson(response, map);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
