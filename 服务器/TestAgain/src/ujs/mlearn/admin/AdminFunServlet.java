package ujs.mlearn.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.dao.TeacherDao;
import ujs.mlearn.dao.UserDao;
import ujs.mlearn.dao.impl.TeacherDaoImpl;
import ujs.mlearn.dao.impl.UserDaoImpl;
import ujs.mlearn.entity.Student;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class AdminFunServlet
 */
public class AdminFunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFunServlet() {
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
		System.out.println("管理员功能:"+op);
		if(request.getSession().getAttribute("admin")==null) {
			System.out.println("管理员未登陆");
			response.sendRedirect(request.getContextPath()+"/adminLogin.jsp");
		}
		if (op.equals("register")) {
			register(request,response);
		}else if (op.equals("addUser")) {
			addUser(request,response);
		}else if (op.equals("adminTea")) {
			adminTea(request,response);
		}else if (op.equals("adminStu")) {
			adminStu(request,response);
		}else if (op.equals("editStu")) {
			editStu(request,response);
		}else if (op.equals("editTea")) {
			editTea(request,response);
		}else if (op.equals("modStu")) {
			modStu(request,response);
		}else if (op.equals("modTea")) {
			modTea(request,response);
		}
	}


	private void modTea(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teacherID =Integer.parseInt(request.getParameter("teacherID"));
		String teacherName=request.getParameter("teacherName");
		String password=request.getParameter("password");
		String sex=request.getParameter("sex");
		String email=request.getParameter("email");
		String signature=request.getParameter("signature");
		Teacher teacher=new Teacher(teacherID, teacherName, password, sex, email, "", signature, 0, "");
		TeacherDao tDao=new TeacherDaoImpl();
		tDao.update(teacher);
		System.out.println("修改教师信息成功");
		adminTea(request, response);
	}

	private void modStu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int studentID =Integer.parseInt(request.getParameter("studentID"));
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String sex=request.getParameter("sex");
		String email=request.getParameter("email");
		String signature=request.getParameter("signature");
		Student student=new Student(studentID, username, password, sex, email, "", signature, 0, "");		
		UserDao uDao=new UserDaoImpl();
		uDao.update(student);
		System.out.println("修改学生信息成功");
		adminStu(request, response);
	}

	private void editTea(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teacherID=Integer.parseInt(request.getParameter("teacherID"));	
		TeacherDao tDao=new TeacherDaoImpl();
		Teacher teacher=tDao.findById(teacherID);
//		System.out.println(teacher.toString());
		teacher.setLogintime(teacher.getLogintime().substring(0, 19));
		request.setAttribute("teacher", teacher);
		request.getRequestDispatcher("/WEB-INF/admin/teacher/modTea.jsp").forward(request, response);
	}

	private void editStu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int studentID=Integer.parseInt(request.getParameter("studentID"));
		UserDao uDao=new UserDaoImpl();
		Student student=uDao.findById(studentID);
		student.setLogintime(student.getLogintime().substring(0, 19));
//		System.out.println(student.toString());
		request.setAttribute("student", student);
		request.getRequestDispatcher("/WEB-INF/admin/student/modStu.jsp").forward(request, response);
		
	}

	private void adminStu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao uDao=new UserDaoImpl();
		List<Student> sList= uDao.findAll();
		request.setAttribute("sList",sList);;
		request.getRequestDispatcher("/WEB-INF/admin/student/studentList.jsp").forward(request, response);	
	}

	private void adminTea(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TeacherDao tDao=new TeacherDaoImpl();
		List<Teacher> tList= tDao.findAll();
		request.setAttribute("tList",tList);;
		request.getRequestDispatcher("/WEB-INF/admin/teacher/teacherList.jsp").forward(request, response);	
	}

	private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userType=request.getParameter("userType");
		String userName=request.getParameter("userName");
		String userPass=request.getParameter("userPass");
		String userEmail=request.getParameter("userEmail");
		if (userName.equals("")||userPass.equals("")||userName==null||userPass==null) {
			System.out.println("用户名或密码为空");
			request.setAttribute("message", "用户名密码不能为空");
			request.getRequestDispatcher("/WEB-INF/admin/register/register.jsp").forward(request, response);
			return;
		}
		if (userType.equals("老师")) {
			TeacherDao tDao=new TeacherDaoImpl();
			if (tDao.find(userEmail)!=null) {
				request.setAttribute("message", "邮箱重复");
				request.getRequestDispatcher("/WEB-INF/admin/register/register.jsp").forward(request, response);
				return;
			}
			Teacher teacher=new Teacher(0, userName, userPass, "", userEmail, "", "", 0, "");
			tDao.add(teacher);
		}else if (userType.equals("学生")) {
			UserDao uDao=new UserDaoImpl();
			if (uDao.find(userEmail)!=null) {
				request.setAttribute("message", "邮箱重复");
				request.getRequestDispatcher("/WEB-INF/admin/register/register.jsp").forward(request, response);
				return;
			}
			Student student=new Student(0, userName, userPass, "", userEmail, "", "", 0, "");
			uDao.add(student);
		}
		request.setAttribute("message", "注册成功");
		request.getRequestDispatcher("/WEB-INF/admin/register/register.jsp").forward(request, response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/admin/register/register.jsp").forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
