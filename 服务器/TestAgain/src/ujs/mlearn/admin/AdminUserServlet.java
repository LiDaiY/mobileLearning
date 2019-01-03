package ujs.mlearn.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminUserServlet
 */
public class AdminUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserServlet() {
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
		System.out.println("管理员"+op);
		if (op.equals("login")) {
			login(request,response);
		}else if (op.equals("logout")) {
			logout(request,response);
		}
		

	}


	private void logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getSession().invalidate();
//			GlobalParams.teacher=new Teacher();
			response.sendRedirect(request.getContextPath()+"/adminLogin.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account =request.getParameter("account");
		String password = request.getParameter("password");		
		if (account.equals("root")&&password.equals("root")) {
			 request.getSession().setAttribute("admin","1");
//			 request.getRequestDispatcher("adIndex/adIndex.jsp").forward(request, response);
			 request.getRequestDispatcher("/adIndex/adIndex.jsp").forward(request, response);
			 System.out.println("管理员登陆成功");
		}else {
			request.setAttribute("error", "用户名或密码错误");
			 request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
