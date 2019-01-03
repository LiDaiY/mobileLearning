package ujs.mlearn.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.HomeworkDao;
import ujs.mlearn.dao.StuHomeworkDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.HomeworkDaoImpl;
import ujs.mlearn.dao.impl.StuHomeworkDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Homework;
import ujs.mlearn.entity.StuHomework;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebHomeworkServlet
 */
public class WebHomeworkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebHomeworkServlet() {
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
		if (op.equals("goShowPage")) {
			goShowPage(request,response);
		}else if (op.equals("addHomework")) {
			addHomework(request,response);
		}else if (op.equals("findCourseHw")) {//找到这么门所有的作业
			findCourseHw(request,response);
		}else if (op.equals("findMyCourse")) {//查看布置的作业之前要先看有哪些课程
			findMyCourse(request,response);
		}else if (op.equals("findHwDetail")) {
			findHwDetail(request,response);//查看作业详细信息
		}else if (op.equals("modHomework")) {
			modHomework(request,response);
		}else if (op.equals("findStuHw")) {
			//找学生所提交的作业
			findStuHw(request,response);
		}else if (op.equals("downloadWork")) {
			//下载某一个作业
			downloadWork(request,response);
		}
	}

	private void downloadWork(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		int shwID=Integer.parseInt((request.getParameter("shwID")));
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		StuHomeworkDao sDao=new StuHomeworkDaoImpl();
		StuHomework sHomework= sDao.findOneWork(shwID);
		String fileName=sHomework.getStuWorkTitle();
		response.setContentType(getServletContext().getMimeType(fileName));
		try {

			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String path=getServletContext().getRealPath("/")+sHomework.getHwUrl();
		path = path.replaceAll("/", "\\\\");
		File file=new File(path);
		response.setHeader("Content-Length", String.valueOf(file.length()));//需要这句才能获取文件大小
		System.out.println("下载作业，地址是："+path);

		try {
			InputStream in;
			in = new BufferedInputStream(new FileInputStream(
					path));
	        byte[] buff = new byte[1024];
	        int len = 0;
	        OutputStream out;
			out = response.getOutputStream();
	        while ((len = in.read(buff)) != -1) {
	            out.write(buff, 0, len);
	        }
	        in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
}

	private void findStuHw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StuHomeworkDao sDao=new StuHomeworkDaoImpl();
		int hwID=Integer.parseInt((request.getParameter("hwID")));
		List<StuHomework> sHomeworks=sDao.findStuHwByHwID(hwID);
		request.setAttribute("sHomeworks", sHomeworks);
		request.getRequestDispatcher("/WEB-INF/teacher/homework/stuWork.jsp").forward(request, response);
	}

	private void modHomework(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HomeworkDao hDao=new HomeworkDaoImpl();

		int courseID=Integer.parseInt((request.getParameter("courseID")));
		int hwID=Integer.parseInt((request.getParameter("hwID")));
		String hwContent=request.getParameter("hwContent");
		String hwTitle=request.getParameter("hwTitle");
		Homework homework=new Homework(hwID, 0, 0, hwContent, "",hwTitle);
		hDao.updateHomework(homework);
		request.getRequestDispatcher("/WebHomeworkServlet?action=findCourseHw&courseID="+courseID).forward(request, response);
	}

	private void findHwDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HomeworkDao hDao=new HomeworkDaoImpl();
		int hwID=Integer.parseInt((request.getParameter("hwID")));
		Homework homework=hDao.findHwByHwID(hwID);
		request.setAttribute("homework", homework);
		request.getRequestDispatcher("/WEB-INF/teacher/homework/homeworkDetail.jsp").forward(request, response);
	}

	private void findMyCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		CourseDao cou = new CourseDaoImpl();
		List<Course> courses = cou.findMyCourse(teacherID);
		request.setAttribute("courses",courses);
		request.setAttribute("teacherID",teacherID);
		request.getRequestDispatcher("/WEB-INF/teacher/homework/courselist.jsp").forward(request, response);		
	}

	private void findCourseHw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseID=Integer.parseInt((request.getParameter("courseID")));
		HomeworkDao hDao=new HomeworkDaoImpl();
		List<Homework> hList=hDao.findHwByCourseID(courseID);
		request.setAttribute("hList", hList);
		request.getRequestDispatcher("/WEB-INF/teacher/homework/hwList.jsp").forward(request, response);
	}

	private void addHomework(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HomeworkDao hDao=new HomeworkDaoImpl();
		
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		String courseName=request.getParameter("courseName");
		CourseDao courseDao=new CourseDaoImpl();
		Course course=courseDao.findIdByName(courseName, teacherID);
		int courseID=course.getCourseID();
		String hwContent=request.getParameter("hwContent");
		String hwTitle=request.getParameter("hwTitle");
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis);  //只有年月日  与MySQL中的DATE相对应  
        Time time = new Time(currentTimeMillis);  //只有时分秒  与MySQL中的TIME相对应  
        String publishTime = date.toString() + " " + time.toString();//中间要加空格才行
		
        Homework homework=new Homework(0, courseID, teacherID, hwContent, publishTime,hwTitle);
        
        hDao.addHomework(homework);
        
        request.getRequestDispatcher("/WebHomeworkServlet?action=findCourseHw&courseID="+courseID).forward(request, response);
	}

	private void goShowPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/teacher/homework/addhomework.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
