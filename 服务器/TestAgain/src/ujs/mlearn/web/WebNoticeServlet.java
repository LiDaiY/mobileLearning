package ujs.mlearn.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.NoticeDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.NoticeDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Notice;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebNoticeServlet
 */
public class WebNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Teacher teacher=(Teacher) request.getSession().getAttribute("teacher");
		if (teacher==null) {
			System.out.println("用户未登陆");
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		String op  = request.getParameter("action");
		if("addNotice".equals(op)) {
			addNotice(request,response);
		}else if ("findMyNotice".equals(op)) {
			findMyNotice(request,response);
		}else if ("findNotice".equals(op)) {
			findNotice(request,response);
		}else if ("delNotice".equals(op)) {
			delNotice(request,response);
		}else if ("udNotice".equals(op)) {
			udNotice(request,response);
		}else if ("goShowPage".equals(op)) {
			goShowPage(request,response);
		}
	}


	private void goShowPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("通知页面跳转");
		request.getRequestDispatcher("/WEB-INF/teacher/notice/addnotice.jsp").forward(request, response);
	}

	private void udNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDao noti = new NoticeDaoImpl();
		Notice notic = new Notice();
		
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeContent = request.getParameter("noticeContent");
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		String noticeTime = dateString;
		int  noticeID =Integer.parseInt(request.getParameter("noticeID")) ;
		notic.setNoticeContent(noticeContent);
		notic.setNoticeTitle(noticeTitle);
		notic.setNoticeTime(noticeTime);
		notic.setNoticeID(noticeID);
		noti.updateNotice(notic);
		//System.out.print(notic);
		request.getRequestDispatcher("/WebNoticeServlet?action=findMyNotice").forward(request, response);		
	}

	private void delNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDao noti = new NoticeDaoImpl();
		int noticeID = Integer.parseInt(request.getParameter("noticeID"));
		String teacherID=request.getParameter("teacherID");
		System.out.println("老师ID："+teacherID);
		noti.delNotice(noticeID);
		request.getRequestDispatcher("/WebNoticeServlet?action=findMyNotice&teacherID="+teacherID).forward(request, response);
	}

	private void findNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDao noti = new NoticeDaoImpl();
		int noticid = Integer.parseInt(request.getParameter("noticeID"));
		Notice notice=noti.findNotice(noticid);
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/teacher/notice/modnotice.jsp").forward(request, response);
	}

	private void findMyNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		NoticeDao noti = new NoticeDaoImpl();
		List<Notice> notics = noti.findMyNotice(teacherID);
		request.setAttribute("notices",notics);
		request.setAttribute("teacherID",teacherID);
		request.getRequestDispatcher("/WEB-INF/teacher/notice/noticelist.jsp").forward(request, response);		
	}

	private void addNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDao noti = new NoticeDaoImpl();
		CourseDao courseDao=new CourseDaoImpl();
		String notictitle = request.getParameter("noticeTitle");
		String noticcontent = request.getParameter("noticeContent");
		String courseName=request.getParameter("courseName");
		
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		Course course=courseDao.findIdByName(courseName, teacherID);
		
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		String dateString = formatter.format(currentTime);
		String notictime = dateString;
		
		Notice notice = new Notice();
		notice.setNoticeContent(noticcontent);
		notice.setNoticeTime(notictime);
		notice.setNoticeTitle(notictitle);
		notice.setTeacherID(teacherID);
		notice.setCourseName(course.getCourseName());
		notice.setCourseID(course.getCourseID());
		noti.addNotice(notice);
//		request.setAttribute("teacherID", teacherID);
		request.getRequestDispatcher("/WebNoticeServlet?action=findMyNotice").forward(request, response);
	}

}
