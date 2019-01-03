package ujs.mlearn.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebUserServlet
 */
public class WebUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String op  = request.getParameter("operation");
		System.out.println(op);
		if(op.equals("register")){
			register(request,response);
		}else if(op.equals("login")) {
			login(request,response);
		}else if (op.equals("logout")) {
			logout(request,response);
		}
	}



	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			request.getSession().invalidate();
//			GlobalParams.teacher=new Teacher();
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email =request.getParameter("email");
		String password = request.getParameter("password");
		TeacherDao teacherDao=new TeacherDaoImpl();
//		GlobalParams.teacher=teacherDao.login(email, password);
//		Teacher teacher=GlobalParams.teacher;
		Teacher teacher=teacherDao.login(email, password);
		 if(teacher == null) {
			 request.setAttribute("error", "用户名或密码错误");
			 request.getRequestDispatcher("login.jsp").forward(request, response);
			 System.out.println("失败");
		 }else {
			 long currentTimeMillis = System.currentTimeMillis();
			 Date date = new Date(currentTimeMillis);  //只有年月日  与MySQL中的DATE相对应  
		     Time time = new Time(currentTimeMillis);  //只有时分秒  与MySQL中的TIME相对应  
		     String logintime = date.toString() + " " + time.toString();//中间要加空格才行
		     teacherDao.uduser("logintime", logintime, teacher.getTeacherID());
//			System.out.println(GlobalParams.teacher);
			 request.getSession().setAttribute("teacher",teacher);
			 request.getSession().setAttribute("login","1");
			 request.getRequestDispatcher("/index/index.jsp").forward(request, response);
			 System.out.println("成功");
		 }
	}

	private void register(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
