package ujs.mlearn.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ujs.mlearn.Utils.CommonUtil;
import ujs.mlearn.dao.HomeworkDao;
import ujs.mlearn.dao.StuHomeworkDao;
import ujs.mlearn.dao.impl.HomeworkDaoImpl;
import ujs.mlearn.dao.impl.StuHomeworkDaoImpl;
import ujs.mlearn.entity.Homework;
import ujs.mlearn.entity.SentMessage;
import ujs.mlearn.entity.StuHomework;

/**
 * Servlet implementation class HomeworkServlet
 */
public class HomeworkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeworkServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String op = request.getParameter("operation");
		System.out.println(op);
		if (op.equals("findCourseHw")) {
			findCourseHw(request, response);// 找一门课的所有作业
		} else if (op.equals("upHomework")) {
			upHomework(request, response);// 学生上传作业
		}
	}

	private void upHomework(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("学生开始上传作业");
		request.setCharacterEncoding("utf-8");
		int studentID = Integer.parseInt((request.getParameter("studentID")));
		int hwID = Integer.parseInt((request.getParameter("hwID")));
		String studentName = request.getParameter("studentName");
//		request.getParameter("stuWorkTitle").getBytes("");
		String stuWorkTitle = new String(
				request.getParameter("stuWorkTitle").getBytes("iso-8859-1"),"utf-8");
//		String stuWorkTitle ="";
		String hwUrl = "";
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis); // 只有年月日 与MySQL中的DATE相对应
		Time time = new Time(currentTimeMillis); // 只有时分秒 与MySQL中的TIME相对应
		String subTime = date.toString() + " " + time.toString();// 中间要加空格才行

		String fileDir="";//完整的文件目录
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = new ArrayList<>();
		try {
			list = upload.parseRequest(request);// 解析请求
			for (FileItem item : list) {
				if (!item.isFormField()) {// 如果是文件
//					stuWorkTitle =new String( item.getName().getBytes("UTF-8"),"UTF-8");// 这是上传的文件名，包括后缀
//					stuWorkTitle =item.getName();// 这是上传的文件名，包括后缀

					if (!stuWorkTitle.equals("")) {
						hwUrl = "WEB-INF/res/homework/作业号" + hwID +"/学号"+studentID+"/"+ stuWorkTitle;
						fileDir=this.getServletContext().getRealPath("/")+"WEB-INF/res/homework/作业号" + hwID +"/学号"+studentID;
						fileDir = fileDir.replaceAll("\\\\", "/");// 这就是最后的目录路径
					}
					File dir = new File(fileDir);
					if(!dir.exists()) {//如果目录不存在就创建目录
						dir.mkdirs();
						System.out.println("目录不存在，现已创建成功");
					}
					String url = fileDir+"/" + stuWorkTitle;
					System.out.println("文件存放路径"+url);
					File serverFile = new File(url);
					try {
						item.write(serverFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("成功上传作业");
				}
			}
		} catch (Exception e) {
			SentMessage message = new SentMessage(0, "上传作业失败");
			CommonUtil.renderJson(response, message);
			e.printStackTrace();
		}
		StuHomeworkDao sDao = new StuHomeworkDaoImpl();
		StuHomework sHomework = new StuHomework(0, 0, hwID, subTime, hwUrl, stuWorkTitle, studentID, studentName);
		System.out.println(sHomework);
		StuHomework preHw=sDao.findWork(hwID,studentID);
		if (preHw==null) {
			sDao.upHomework(sHomework);
		}else {
			sDao.upHomework(sHomework, preHw.shwID);
		}

		SentMessage message = new SentMessage(1, "上传作业成功");
		CommonUtil.renderJson(response, message);
	}

	private void findCourseHw(HttpServletRequest request, HttpServletResponse response) {
		int courseID = Integer.parseInt((request.getParameter("courseID")));
		HomeworkDao hDao = new HomeworkDaoImpl();
		List<Homework> hList = hDao.findHwByCourseID(courseID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hList", hList);
		CommonUtil.renderJson(response, map);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


}
