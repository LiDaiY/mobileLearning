package ujs.mlearn.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ujs.mlearn.Utils.CommonUtil;
import ujs.mlearn.dao.BbsDao;
import ujs.mlearn.dao.UserDao;
import ujs.mlearn.dao.impl.BbsDaoImpl;
import ujs.mlearn.dao.impl.UserDaoImpl;
import ujs.mlearn.entity.SentMessage;
import ujs.mlearn.entity.Student;




public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserServlet() throws UnknownHostException {
        super();
//        InetAddress address=InetAddress.getLocalHost();
//        System.out.println(address.getHostAddress());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String op  = request.getParameter("operation");
		System.out.println(op);
		if(op.equals("register")){
			register(request,response);
		}else if(op.equals("login")) {
			login(request,response);
		}else if(op.equals("logout")) {
			logout(request,response);
		}else if (op.equals("upuser")) {
			upuser(request,response);
		}else if (op.equals("upphoto")) {//上传头像
			upphoto(request,response);
		}else if (op.equals("test")) {
			String url=this.getServletContext().getRealPath("/");
			System.out.println(url);
			url=url+"/1.txt";
//			File dir=new File(url);
//			if(!dir.exists()) {
//				dir.mkdirs();
//				dir.createNewFile();
//			}
//			url = url.replaceAll("\\\\", "/");
//			System.out.println(url);
//			if(!dir.exists()) {
//				dir.mkdirs();
//			}
		}
	}


	private void upphoto(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("收到修改头像信息");
		UserDao use = new UserDaoImpl();
		int userid = Integer.parseInt(request.getParameter("userID"));
		Student u = use.findById(userid);
		String url = u.getPhoto();
		
		url=this.getServletContext().getRealPath("/")+url;
//		System.out.println(url);
		url = url.replaceAll("\\\\", "/");//这就是最后的url，包括文件名
		File file = new File(url);
		if (file.exists()) {
			System.out.println("原先就有头像");
			file.delete();//删除原先的头像
		}
		String dirName=url.replace(file.getName(), "");//文件名，其实都为pic.jpg
		File dir=new File(dirName);//文件夹的路径
		if(!dir.exists()) {//若文件夹不存在，则创建文件夹
			dir.mkdirs();
			System.out.println("文件夹不存在，现已创建");
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			FileItemIterator itemIterator = upload.getItemIterator(request);
			while (itemIterator.hasNext()) {
				FileItemStream itemStream = itemIterator.next();
				if (itemStream.isFormField()) {
					InputStream inputStream = itemStream.openStream();
					//把输入流 -> 字符串
					String fieldName = itemStream.getFieldName();
					System.out.println("获得的文件名"+fieldName);
				} else {
					InputStream inputStream = itemStream.openStream();
					//把输入流 -> 文件
					saveFile(inputStream, url);
					System.out.println("成功上传头像");
					response.getWriter().println(url);
//					value=tempDir+fileName;
				}
			}
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	private void saveFile(InputStream is, String url) {
		File serverFile = new File(url);
//		String fileName=serverFile.getName();
//		String dirName=url.replace(fileName, "");
//		File dir=new File(dirName);
//		if(!dir.exists()) {
//			dir.mkdirs();
//		}
		 try {
			 FileOutputStream out = new FileOutputStream(serverFile);
			 
			 int bytesWritten = 0;
			 int bytecount = 0;
			 byte[] bytes =new byte[1024];
			 
			 while((bytecount = is.read(bytes)) != -1){
				 out.write(bytes, bytesWritten, bytecount);
			 }
			 System.out.println("上传头像完成");
			 is.close();
			 out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return serverFileName;
	}

	//用于修改用户信息
	private void upuser(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		UserDao userDao=new UserDaoImpl();
		String attribute=new String(
				request.getParameter("attribute").getBytes("iso-8859-1"),"utf-8");
		String value=new String(
				request.getParameter("value").getBytes("iso-8859-1"),"utf-8");
		int userid=Integer.parseInt(request.getParameter("userID"));
		try {
		userDao.uduser(attribute, value, userid);
//		if (attribute.equals("username")) {
//			BbsDao bbsDao=new BbsDaoImpl();
//			bbsDao.upUserName(userid, value);
//		}
		SentMessage message=new SentMessage(1,"修改成功");
		CommonUtil.renderJson(response, message);
		}catch (Exception e) {
			SentMessage message=new SentMessage(0,"修改失败");
			CommonUtil.renderJson(response, message);		
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}


	private void login(HttpServletRequest request, HttpServletResponse response) {
		SentMessage message;
		String email =request.getParameter("email");
		String password = request.getParameter("password");
		UserDao use = new UserDaoImpl();
		 Student user = use.login(email, password);
		 if(user == null) {
			// request.setAttribute("error", "用户名或密码错误！");
			 message = new SentMessage(0, "用户名或密码错误");
			 CommonUtil.renderJson(response, message);
		 }else {
			 long currentTimeMillis = System.currentTimeMillis();
			 Date date = new Date(currentTimeMillis);  //只有年月日  与MySQL中的DATE相对应  
		     Time time = new Time(currentTimeMillis);  //只有时分秒  与MySQL中的TIME相对应  
		     String logintime = date.toString() + " " + time.toString();//中间要加空格才行
			 use.uduser("logintime", logintime, user.getUserId());
			 request.getSession().setAttribute("user",user);
			 request.getSession().setAttribute("login","1");
			 Map<String,Object> map = new HashMap<String,Object>();
			 map.put("code", 1);
			 map.put("user", user);
			 request.getSession().setAttribute("login",1);
			 CommonUtil.renderJson(response,map);
		 }
	}


	private void register(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		UserDao use=new UserDaoImpl();
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String username=new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
//		String username=request.getParameter("username");
		SentMessage message;
		if(use.find(email) == null){
			Student user = new Student();
			user.setEmail(email);
			user.setPassword(password);
			user.setUsername(username);
			user.setSex("男");
			String photo="res/user/"+email+"/pic.jpg";
			user.setPhoto(photo);
			System.out.println("注册时的用户信息"+user.toString());
			use.add(user);
			message = new SentMessage(1,"注册成功");
		}else {
			message = new SentMessage(0,"该账号已经被注册");
		}
		CommonUtil.renderJson(response,message);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
