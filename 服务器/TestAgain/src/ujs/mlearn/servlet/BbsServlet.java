package ujs.mlearn.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.Utils.CommonUtil;
import ujs.mlearn.dao.BbsDao;
import ujs.mlearn.dao.ReplyDao;
import ujs.mlearn.dao.StudentCourseDao;
import ujs.mlearn.dao.impl.BbsDaoImpl;
import ujs.mlearn.dao.impl.ReplyDaoImpl;
import ujs.mlearn.dao.impl.StudentCourseDaoImpl;
import ujs.mlearn.entity.BbsTheme;
import ujs.mlearn.entity.ReplyPost;
import ujs.mlearn.entity.SentMessage;
import ujs.mlearn.entity.StudentCourse;

/**
 * Servlet implementation class BbsServlet
 */
public class BbsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsServlet() {
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
		if (op.equals("findCourseList")) {//论坛显示课程界面
			findCourseList(request,response);
		}else if (op.equals("findPostList")) {//显示这门课的主题帖
			findPostList(request,response);
		}else if (op.equals("sendPost")) {//发帖
			addPost(request,response);
		}else if (op.equals("findReplyList")) {
			findReplyList(request,response);//显示回帖
		}else if (op.equals("sendReply")) {
			sendReply(request,response);//学生回帖
		}else if (op.equals("deleteReply")) {//删除回帖
			deleteReply(request,response);
			System.out.println("删除成功");
		}else if (op.equals("deletePost")) {//删除主题帖
			deletePost(request,response);
			System.out.println("删除帖子成功");
			
		}
			
		
	}

	private void deletePost(HttpServletRequest request, HttpServletResponse response) {
		int postID=Integer.parseInt(request.getParameter("postID"));
		BbsDao bDao=new BbsDaoImpl();
		bDao.deletePost(postID);
		SentMessage message=new SentMessage(1,"删除成功");
		CommonUtil.renderJson(response, message);
	}

	private void deleteReply(HttpServletRequest request, HttpServletResponse response) {
		int postID=Integer.parseInt(request.getParameter("postID"));
		int replyID=Integer.parseInt(request.getParameter("replyID"));
		ReplyDao replyDao=new ReplyDaoImpl();
		replyDao.deleteRep(replyID);
        BbsDao bbsDao=new BbsDaoImpl();
        bbsDao.stateChange(21, postID);
		SentMessage message=new SentMessage(1,"删除成功");
		CommonUtil.renderJson(response, message);
	}

	private void sendReply(HttpServletRequest request, HttpServletResponse response) {
		int postID=Integer.parseInt(request.getParameter("postID"));
		String replyContent=request.getParameter("replyContent");
		int userID=Integer.parseInt(request.getParameter("userID"));
		String userName=request.getParameter("userName");
		String userEmail=request.getParameter("userEmail");
		
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis);  //只有年月日  与MySQL中的DATE相对应  
        Time time = new Time(currentTimeMillis);  //只有时分秒  与MySQL中的TIME相对应  
        String replyTime = date.toString() + " " + time.toString();//中间要加空格才行
        
        ReplyPost replyPost=new ReplyPost(0, postID, userName, replyContent, replyTime, 0, 0, userEmail, userID,"");
//        System.out.println(replyPost.toString());
        ReplyDao replyDao=new ReplyDaoImpl();
        replyDao.addReply(replyPost);
        //学生回答问题后，主题帖回答数加1
        BbsDao bbsDao=new BbsDaoImpl();
        bbsDao.stateChange(20, postID);
		SentMessage message=new SentMessage(1,"回帖成功");
		CommonUtil.renderJson(response, message);
		
	}

	private void findReplyList(HttpServletRequest request, HttpServletResponse response) {
		int postID=Integer.parseInt(request.getParameter("postID"));
		ReplyDao replyDao=new ReplyDaoImpl();
		List<ReplyPost> replyPosts=replyDao.findPostReply(postID);
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("replyList", replyPosts);
		CommonUtil.renderJson(response, map);
	}

	private void addPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		int studentID=Integer.parseInt(request.getParameter("studentID"));
//		String studentName=new String(
//				request.getParameter("studentName").getBytes("iso-8859-1"),"utf-8");

//		String postTitle=new String(
//				request.getParameter("title").getBytes("iso-8859-1"),"utf-8");
//		String postContent=new String(
//				request.getParameter("content").getBytes("iso-8859-1"),"utf-8");
//		String studentEmail=new String(
//				request.getParameter("studentEmail").getBytes("iso-8859-1"),"utf-8"); 
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		String studentName=request.getParameter("studentName");
		String postTitle=request.getParameter("title");
		String postContent=request.getParameter("content");
		String studentEmail=request.getParameter("studentEmail"); 
		long currentTimeMillis = System.currentTimeMillis();
		Date date = new Date(currentTimeMillis);  //只有年月日  与MySQL中的DATE相对应  
        Time time = new Time(currentTimeMillis);  //只有时分秒  与MySQL中的TIME相对应  
        String postTime = date.toString() + " " + time.toString();//中间要加空格才行
		
		BbsTheme bbsTheme=new BbsTheme(0, studentID, studentName, courseID, 
				postTitle, postContent, 0, postTime, "", 0, studentEmail);
//		System.out.println(bbsTheme.toString());
		BbsDao bDao=new BbsDaoImpl();
		bDao.addPost(bbsTheme);//发帖
		SentMessage message=new SentMessage(1,"发帖成功");
		CommonUtil.renderJson(response, message);
	}

	private void findPostList(HttpServletRequest request, HttpServletResponse response) {
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		BbsDao bDao=new BbsDaoImpl();
		List<BbsTheme> bbsThemes=bDao.findNoteByCourseID(courseID);
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("postList", bbsThemes);
		CommonUtil.renderJson(response, map);
		
	}

	private void findCourseList(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Integer> stuNumMap = new HashMap<String,Integer>();//JSON的key必须为string类型
		Map<String,Integer> noteNumMap = new HashMap<String,Integer>();
		int studentID=Integer.parseInt(request.getParameter("studentID"));
		
		StudentCourseDao sc=new StudentCourseDaoImpl();
		BbsDao bDao=new BbsDaoImpl();
		
		List<StudentCourse> sCourses=new ArrayList<>();
		sCourses=sc.findAll(studentID);
		for(StudentCourse sCourse:sCourses) {
			int id=sCourse.getCourseID();
			int stuNum=sc.findCountByCourseID(id);//遍历每门课学生的数量
			int noteNum=bDao.findCount(id);
			
			String nid=String.valueOf(id);
			stuNumMap.put(nid, stuNum);
			noteNumMap.put(nid, noteNum);
		}
		map.put("relationList",sCourses);
		map.put("stuNum", stuNumMap);
		map.put("noteNum", noteNumMap);
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
