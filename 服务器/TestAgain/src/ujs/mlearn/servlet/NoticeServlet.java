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
import ujs.mlearn.dao.NoticeDao;
import ujs.mlearn.dao.StudentCourseDao;
import ujs.mlearn.dao.impl.NoticeDaoImpl;
import ujs.mlearn.dao.impl.StudentCourseDaoImpl;
import ujs.mlearn.entity.Notice;
import ujs.mlearn.entity.StudentCourse;

/**
 * Servlet implementation class NoticeServlet
 */
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeServlet() {
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
		if("findMyNotice".equals(op)) {
			findMyNotice(request,response);//这是用于查找学生所订阅的课程的所有通知
		}
	}

	private void findMyNotice(HttpServletRequest request, HttpServletResponse response) {
		NoticeDao noti = new NoticeDaoImpl();
		int studentID=Integer.parseInt(request.getParameter("studentID"));
		List<Notice> notices=new ArrayList<>();
		//先通过学生Id取得学生所订阅的课程
		StudentCourseDao studentCourseDao=new StudentCourseDaoImpl();
		List<StudentCourse> scRelation=(List<StudentCourse>) studentCourseDao.findAll(studentID);//取出学生的选课关系
		for(StudentCourse studentCourse:scRelation) {
			int courseID=studentCourse.getCourseID();
			notices.addAll(noti.findCourseNotice(courseID));
		}
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("notices", notices);
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
