package ujs.mlearn.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import ujs.mlearn.dao.CourseDao;
import ujs.mlearn.dao.CourseMaterialDao;
import ujs.mlearn.dao.impl.CourMaterialDaoImpl;
import ujs.mlearn.dao.impl.CourseDaoImpl;
import ujs.mlearn.entity.Course;
import ujs.mlearn.entity.CourseMaterial;
import ujs.mlearn.entity.Teacher;

/**
 * Servlet implementation class WebMaterialServlet
 */
public class WebMaterialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebMaterialServlet() {
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
		System.out.println(op);
		if (op.equals("goShowPage")) {
				goShowPage(request,response);//页面的跳转
		}else if (op.equals("addVideo")) {
			addVideo(request,response);
		}else if (op.equals("findCourseList")) {
			findCourseList(request,response);
		}else if (op.equals("findCourseRes")) {
			findCourseRes(request,response);
		}else if (op.endsWith("downloadRes")) {
			downloadRes(request,response);
		}
	}

	private void downloadRes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("准备下载");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		int resID=Integer.parseInt(request.getParameter("resID"));
		CourseMaterialDao cDao=new CourMaterialDaoImpl();
		CourseMaterial cMaterial=cDao.findMaterialByID(resID);
		String fileName=cMaterial.getResTitle();
		response.setContentType(getServletContext().getMimeType(fileName));
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));	
		String path=getServletContext().getRealPath("/")+cMaterial.getResUrl();
		path = path.replaceAll("\\\\", "/");
		System.out.println("路径"+path);
		File file=new File(path);
		response.setHeader("Content-Length", String.valueOf(file.length()));//需要这句才能获取文件大小
		InputStream in = new BufferedInputStream(new FileInputStream(
				path));

        OutputStream out = response.getOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = in.read(buff)) != -1) {
            out.write(buff, 0, len);
        }
        in.close();
	}

	private void findCourseRes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		CourseMaterialDao cDao=new CourMaterialDaoImpl();
		List<CourseMaterial> materials=cDao.findCourseMaterial(courseID);
		
		request.setAttribute("materials", materials);
		request.getRequestDispatcher("/WEB-INF/teacher/document/reslist.jsp").forward(request, response);
	}

	private void findCourseList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CourseDao courseDao=new CourseDaoImpl();
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		List<Course> courses=courseDao.findMyCourse(teacherID);
		request.setAttribute("courses", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/document/courselist.jsp").forward(request, response);
	}

	private void addVideo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");	
		CourseMaterialDao cDao=new CourMaterialDaoImpl();
		String courseName="";
		String resTitle="";
		int courseID=0;
		String resUrl="";
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		long currentTimeMillis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(currentTimeMillis);  //只有年月日  与MySQL中的DATE相对应  
        Time time = new Time(currentTimeMillis);  //只有时分秒  与MySQL中的TIME相对应  
        String publishTime = date.toString() + " " + time.toString();//中间要加空格才行
        
		CourseMaterial courseMaterial = null;
		CourseDao courseDao=new CourseDaoImpl();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);//解析请求
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		for(FileItem item:list){
			if(item.isFormField()){//如果不是文件
				String value = new String(item.getString().getBytes("ISO-8859-1"),"UTF-8");
				if ("courseName".equals(item.getFieldName())) {
					courseName=value;//拿到用户选择的课程名称，从而拿到课程id
				}
			}
		}
		Course course=courseDao.findIdByName(courseName, teacherID);
		courseID=course.getCourseID();
		for(FileItem item:list){
			if(!item.isFormField()){//如果是文件
				if (item.getName() != null && item.getName() != "") {
					String fileName = item.getName();//这是上传的文件名，包括后缀
					int ii = fileName.lastIndexOf(".");
					String maType = fileName.substring(ii, fileName.length());//文件格式
					if(!maType.equals(".avi") && !maType.equals(".mp4") &&!maType.equals(".rmvb") && !maType.equals(".wmv")&& !maType.equals(".flv")){
						System.out.println("文件格式不对");
						return ;
					}
					String fileDir=this.getServletContext().getRealPath("/")+"res/course/"+courseID+"/material";//文件目录
					/*"\\" 被正则表达式引擎解释为 \，所以在正则表达式中需要使用四个反斜杠。 
					 * 也就是说，前两个反斜杠在字符串中被解释为一个反斜杠，后两个也被解释为一个反斜杠，
					 * 这时解释完毕后变成两个反斜杠，再由正则表达式解释两个反斜杠，就又解释成了一个反斜杠，
					 * 所以，在正则表达式中要匹配一个反斜杠时，需要四个反斜杠。*/
					fileDir = fileDir.replaceAll("\\\\", "/");
					File dir = new File(fileDir);
					if(!dir.exists()) {//如果目录不存在就创建目录
						System.out.println("目录不存在");
						dir.mkdirs();
						System.out.println("目录创建成功");
					}
					long cTime2 = System.currentTimeMillis();
					Date upDate = new Date(cTime2);
					SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					String serverFileName = fmt.format(upDate);
					serverFileName = serverFileName + maType;
					
					resUrl="res/course/"+courseID+"/material/"+serverFileName;
					resTitle=fileName;
					
					long size=item.getSize();
//					double s=size/(1024.0*1024.0);
//					DecimalFormat dFormat=new DecimalFormat("#.0");
//					String resSize=dFormat.format(s)+"MB";
					courseMaterial=new CourseMaterial(0, courseID, publishTime, resTitle, resUrl, teacherID,size);
					String url=fileDir+"/"+serverFileName;
					System.out.println(url);
					File serverFile = new File(url);
					 try {
						item.write(serverFile);
						} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					cDao.addRes(courseMaterial);
				}
			}
		}


		request.setAttribute("msg", "上传成功，请点击我的课程资料查看");
		request.getRequestDispatcher("/WEB-INF/teacher/document/addvideo.jsp").forward(request, response);
	}

	private void goShowPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CourseDao cou = new CourseDaoImpl();
		int teacherID=((Teacher)request.getSession().getAttribute("teacher")).getTeacherID();
		List<Course> courses = cou.findMyCourse(teacherID);
		request.getSession().setAttribute("courses", courses);
		request.getRequestDispatcher("/WEB-INF/teacher/document/addvideo.jsp").forward(request, response);
	}

}
