package ujs.mlearn.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ujs.mlearn.Utils.CommonUtil;
import ujs.mlearn.dao.CourseMaterialDao;
import ujs.mlearn.dao.impl.CourMaterialDaoImpl;
import ujs.mlearn.entity.CourseMaterial;

/**
 * Servlet implementation class ResServlet
 */
public class ResServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResServlet() {
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
		if (op.equals("findResByCourseID")) {
			findResByCourseID(request,response);
		}
	}

	private void findResByCourseID(HttpServletRequest request, HttpServletResponse response) {
		int courseID=Integer.parseInt(request.getParameter("courseID"));
		CourseMaterialDao cDao=new CourMaterialDaoImpl();
		List<CourseMaterial> cList=cDao.findCourseMaterial(courseID);
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("resList", cList);
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
