package ujs.mlearn.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.NoticeDao;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.dao.impl.NoticeDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.Notice;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebCourseServlet
 */
public class WebCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebCourseServlet() {
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
		if (op.equals("findMyCourse")) {
			findMyCourse(request,response);
		}else if (op.equals("addCourse")) {
			addCourse(request,response);
		}else if (op.equals("goShowPage")) {
			goShowPage(request,response);//页面的跳转
		}else if(op.equals("findCourse")) {
			findCourse(request,response);//编辑课程信息前获取课程资料
		}else if (op.equals("editCourse")) {//编辑课程信息
			editCourse(request,response);
		}
	}

	private void editCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		String courseName=request.getParameter("courseName");
		String teacherName=request.getParameter("teacherName");
		String courseAbstract=request.getParameter("courseAbstract");
		String detailInfo=request.getParameter("detailInfo");
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		CourseDao courseDao=new CourseDaoImpl();
		Course course=new Course(courseID, courseName, teacherName, "", courseAbstract, 0, 0, detailInfo);
		courseDao.update(course);
		findMyCourse(request, response);
	}

	private void findCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		CourseDao courseDao=new CourseDaoImpl();
		Course course=courseDao.find(courseID);
		request.setAttribute("course", course);
		request.getRequestDispatcher("/WEB-INF/teacher/course/editcourse.jsp").forward(request, response);
	}

	private void goShowPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/teacher/course/addcourse.jsp").forward(request, response);
	}

	private void addCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		CourseDao courseDao=new CourseDaoImpl();
		System.out.println("开始添加课程");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);//解析请求
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Course course=new Course();

		for(FileItem item:list){
			if(item.isFormField()){//如果不是文件
				String value = new String(item.getString().getBytes("ISO-8859-1"),"UTF-8");
				if("courseName".equals(item.getFieldName())) {//这个方法拿到的是表单的键
					course.setCourseName(value);
				} else if ("teacherName".equals(item.getFieldName())) {
					course.setTeacherName(value);
				} else if ("courseAbstract".equals(item.getFieldName())) {
					course.setCourseAbstract(value);
				} else if ("detailInfo".equals(item.getFieldName())) {
					course.setDetailInfo(value);
				} else if ("teacherID".equals(item.getFieldName())) {
					course.setTeacherID(Integer.parseInt(value));
				}
				
			} 
		}
//		int teacherID=GlobalParams.teacher.getTeacherID();
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		course.setTeacherID(teacherID);
		System.out.println(course);
		String path=courseDao.add(course);//这是封面的路径
		for(FileItem item:list){
			if(!item.isFormField()){//如果是文件
				if(item.getName() != null && item.getName() != ""){
					//long upFilze =  item.getSize();
					String fileName = item.getName();//这个方法拿到文件名，注意与getFieldName区分
					int ii = fileName.lastIndexOf(".");
					String tphoto = fileName.substring(ii, fileName.length());
				
					if(!tphoto.equals(".jpg") && !tphoto.equals(".png") && !tphoto.equals(".gif"))  {
						System.out.print("文件格式不对");
						return;
					}
					String url=this.getServletContext().getRealPath("/")+path;
					url = url.replaceAll("\\\\", "/");//这就是最后的url，包括文件名
					File file = new File(url);
					if (file.exists()) {
						System.out.println("原先就有封面");
						file.delete();//删除原先的封面
					}
					String dirName=url.replace(file.getName(), "");//文件名，其实都为cover.jpg
					File dir=new File(dirName);//文件夹的路径
					if(!dir.exists()) {//若文件夹不存在，则创建文件夹
						dir.mkdirs();
						System.out.println("文件夹不存在，现已创建");
					}
					File serverFile = new File(url);
					 try {
						item.write(serverFile);
						} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} 
			}
		}
	
		request.getRequestDispatcher("/WebCourseServlet?action=findMyCourse").forward(request, response);
	}

	private void findMyCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int teacherID = GlobalParams.teacher.getTeacherID();
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		CourseDao cou = new CourseDaoImpl();
		List<Course> courses = cou.findMyCourse(teacherID);
		request.setAttribute("courses",courses);
//		request.setAttribute("teacherID",teacherID);
		request.getRequestDispatcher("/WEB-INF/teacher/course/courselist.jsp").forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
