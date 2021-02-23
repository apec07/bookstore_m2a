package idv.cm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import idv.cm.db.BookVO;
import idv.cm.db.ConnectionFactory;
import idv.cm.db.OrderListVO;
import idv.cm.db.OrderedListDTO;
import idv.cm.db.OrderedListVO;
import idv.cm.db.UserVO;

/**
 * Servlet implementation class OrderListServlet
 */
@WebServlet("/orderedlist")
public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con = null;
	private Gson gson = null;
	private Logger logger =null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// DB connection init()
//		System.out.println("Shopping Servlet init()!");
		logger = LogManager.getLogger();
		logger.traceEntry();
		logger.info("Shopping Servlet init()");
		gson = new Gson();
		// DB connection init()
		ConnectionFactory factory = ConnectionFactory.getInstance();
		logger.traceExit();
		try {
			con = factory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// DB connection close()
//		System.out.println("Shopping Servlet destory()!");
		logger.traceEntry();
		logger.info("Shopping Servlet destory()");
		// release connection
		con = null;
		gson = null;
		logger.traceExit();
		logger = null;
	}

	private List<OrderedListVO> getOrderList(HttpServletRequest req,UserVO user) {
		
		OrderedListDTO dto = new OrderedListDTO();
		List<OrderedListVO> orderList = dto.findOrderDetail(con, user);
		return orderList;
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		logger.traceEntry();
		UserVO user = (UserVO) request.getSession().getAttribute("user");
		PrintWriter out = response.getWriter();
		
		//logger.debug(gson.toJson(getOrderList(request,user)));
		List<OrderedListVO> list = getOrderList(request,user);
		//out.println("Hi, "+user.getName()+" ,Here is yor ordered list - ");
		//out.println(list);
		request.getSession().setAttribute("session_Order", list);
		request.setAttribute("req_Order", list);
		logger.info(gson.toJson(list));
		List<String> uu = Collections.list(request.getAttributeNames());
		for (String s : uu) {
			logger.info("attributeName - "+ s);
		}
		logger.traceExit();
		//RequestDispatcher rd = getServletContext().getRequestDispatcher("/orderlist.jsp");
		//rd.forward(request, response);
		response.sendRedirect(request.getContextPath()+"/orderlist.jsp");
		
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
