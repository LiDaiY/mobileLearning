package ujs.mlearn.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.javafx.collections.MappingChange.Map;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.StudentCourseDao;
import ujs.mlearn.dao.TestDao;
import ujs.mlearn.dao.UserDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.StudentCourseDaoImpl;
import ujs.mlearn.dao.impl.TestDaoImpl;
import ujs.mlearn.dao.impl.UserDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.StudentCourse;
import ujs.mlearn.entity.Teacher;
import ujs.mlearn.entity.Test;
import ujs.mlearn.entity.Student;

/**
 * Servlet implementation class WebTestServlet
 */
public class WebTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static int courseID=0;//定义一个全局的课程id
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Teacher teacher=(Teacher) request.getSession().getAttribute("teacher");
		if (teacher==null) {
			System.out.println("用户未登陆");
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		String op  = request.getParameter("action");
		System.out.println(op);
		if("addTest".equals(op)) {
			addTest(request,response);
		}else if ("findCourseTest".equals(op)) {
			findCourseTest(request,response);
		}else if ("findMyCourse".equals(op)) {
			//老师添加试题前要先进入不同的课程
			findMyCourse(request,response);
		}else if ("findTest".equals(op)) {
			//用于修改或删除试题
			findTest(request,response);
		}else if ("upTest".equals(op)) {
			upTest(request,response);
		}else if ("findResult".equals(op)) {
			//查找这门课学生的成绩和答题情况
			findResult(request,response);
		}
		else if (op.equals("goShowPage")) {
			goShowPage(request,response);
		}
	}

private void goShowPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	request.getRequestDispatcher("/WEB-INF/teacher/test/addtest.jsp").forward(request, response);
	}

private void findResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		StudentCourseDao sCourseDao=new StudentCourseDaoImpl();
		List<StudentCourse> sList=sCourseDao.findByCouresID(courseID);
		HashMap<Integer, String> name=new HashMap<>();
		UserDao userDao=new UserDaoImpl();
		for(StudentCourse sCourse:sList) {
			int userID=sCourse.getStudentID();
			Student user=userDao.findById(userID);
			name.put(userID,user.getUsername());
		}
		
		request.setAttribute("studentcourse", sList);
		request.setAttribute("name", name);
		request.getRequestDispatcher("/WEB-INF/teacher/test/resultlist.jsp").forward(request, response);	
	}

private void upTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	TestDao testdao = new TestDaoImpl();
	Test test = new Test();
	
	String testContent = request.getParameter("testContent");
	String testAnswer = request.getParameter("testAnswer");

	String testOptionA=request.getParameter("testOptionA");
	String testOptionB=request.getParameter("testOptionB");
	String testOptionC=request.getParameter("testOptionC");
	String testOptionD=request.getParameter("testOptionD");
	String testOption = testOptionA+";"+testOptionB+";"+testOptionC+";"+testOptionD;
	int testID = Integer.parseInt(request.getParameter("testID")); 

	
	test.setTestAnswer(testAnswer);
	test.setTestContent(testContent);
	test.setTestOption(testOption);
	test.setTestID(testID);
	testdao.updateTest(test);
	request.getRequestDispatcher("/WebTestServlet?action=findCourseTest&courseID="+courseID).forward(request, response);		
	}

private void findTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	TestDao testdao = new TestDaoImpl();
	int testid = Integer.parseInt(request.getParameter("testID"));
	Test test = testdao.findTest(testid);
	String testoption = test.getTestOption();
	String[]  testop = testoption.trim().split(";");
	
	String testAnswer = test.getTestAnswer();

	String testOptionA = testop[0];
	String testOptionB = testop[1];
	String testOptionC = testop[2];
	String testOptionD = testop[3];
	System.out.println(testOptionA);
	request.setAttribute("testOptionA",testOptionA);
	request.setAttribute("testOptionB",testOptionB);
	request.setAttribute("testOptionC",testOptionC);
	request.setAttribute("testOptionD",testOptionD);
	request.setAttribute("testAnswer", testAnswer);
	request.setAttribute("test", test);
	request.getRequestDispatcher("/WEB-INF/teacher/test/modtest.jsp").forward(request, response);
	}

private void findCourseTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	int courseID=Integer.parseInt((request.getParameter("courseID")));
	this.courseID=courseID;
	TestDao testdao = new TestDaoImpl();
	List<Test> tests = testdao.findCourseTest(courseID);
//	for(Test test:tests) {
//		System.out.println(test+"啊啊啊啊");
//	}
	request.setAttribute("tests", tests);
	request.getRequestDispatcher("/WEB-INF/teacher/test/testlist.jsp").forward(request, response);		
	}

//	private void chooseCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		courseID=Integer.parseInt(request.getParameter("courseID"));
//		request.getRequestDispatcher("admin/test/addtest.jsp").forward(request, response);	
//	}

	private void findMyCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		CourseDao cou = new CourseDaoImpl();
		List<Course> courses = cou.findMyCourse(teacherID);
		request.setAttribute("courses",courses);
		request.setAttribute("teacherID",teacherID);
		request.getRequestDispatcher("/WEB-INF/teacher/test/coursetestlist.jsp").forward(request, response);			
	}

	private void addTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TestDao testdao = new TestDaoImpl();
		Test test = new Test();

		String testContent = request.getParameter("testContent");
		String testAnswer = request.getParameter("testAnswer");

		String testOptionA=request.getParameter("testOptionA");
		String testOptionB=request.getParameter("testOptionB");
		String testOptionC=request.getParameter("testOptionC");
		String testOptionD=request.getParameter("testOptionD");
		String testOption = testOptionA+";"+testOptionB+";"+testOptionC+";"+testOptionD;
		
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		String courseName=request.getParameter("courseName");
		CourseDao courseDao=new CourseDaoImpl();
		Course course=courseDao.findIdByName(courseName, teacherID);
		test.setCourseID(course.getCourseID());
		
		test.setTeacherID(teacherID);
		test.setTestContent(testContent);
		test.setTestOption(testOption);
		test.setTestAnswer(testAnswer);
		
		testdao.addtest(test);
		courseID=course.getCourseID();//全局变量的courseID
		request.getRequestDispatcher("/WebTestServlet?action=findCourseTest&courseID="+course.getCourseID()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
