package com.libapp;

import java.io.IOException;
import java.sql.SQLException;

import com.libapp.dao.DocumentDao;
import com.libapp.dao.LibrarianDao;
import com.libapp.dao.LoginAccessDao;
import com.libapp.dao.MemberDao;
import com.libapp.model.Document;
import com.libapp.model.Librarian;
import com.libapp.model.Member;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Controller
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// Search List
		String search = request.getParameter("search").toString();
		String userType = request.getParameter("type").toString();
		
		if(userType.equals("Member")) {
			String mem = request.getParameter("userData");
			Member memData = new Member(mem);
			request.setAttribute("userData", memData);
		} else {
			String lib = request.getParameter("userData");
			Librarian libData = new Librarian(lib);
			request.setAttribute("userData", libData);
		}
		//Servlet should only fetch and accept the request and no JDBC processing
		// Use Dao the take care of JDBC processing
		DocumentDao docDao = new DocumentDao();
//		System.out.println("--------------" + search);
		
		try {
			Document doc[] = docDao.getDocument(search);
		
		// To display the data, create a JSP file (e.g., ShowInstructor.jsp)
		
		
		// To send the Instructor object ob1 to JSP file. (e.g., ShowInstructor.jsp) 
			request.setAttribute("searchList", doc);
			request.setAttribute("type", userType);
			
			// To call JSP page that will display the data either using dispatcher or send redirect.
			
			RequestDispatcher rd = request.getRequestDispatcher("searchList.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		//Login
		int username = Integer.parseInt(request.getParameter("user"));
		String pwd = request.getParameter("pwd").toString();
		String userType = request.getParameter("type").toString();
		//Servlet should only fetch and accept the request and no JDBC processing
		// Use Dao the take care of JDBC processing
		
		LoginAccessDao authData = new LoginAccessDao();
		String auth = authData.validateCredentials(username, pwd, userType);
//		boolean auth = false;
//		if(username.equals("dev") && pwd.equals("dev123")) {
//			auth = true;
//		}
		
		// To display the data, create a JSP file (e.g., ShowInstructor.jsp)
		
		
		// To send the Instructor object ob1 to JSP file. (e.g., ShowInstructor.jsp) 
//		request.setAttribute("dashboard", ob1);  // fetch this attribute in the JSP file
		
		RequestDispatcher rd;
		
		if(!auth.equals("Incorrect username / User Type") && !auth.equals("Incorrect Password")) {
			if(userType.equals("Member")) {
				MemberDao memData = new MemberDao();
				Member mem = memData.getMemberData(Integer.parseInt(auth));
				request.setAttribute("userData", mem);
			} else {
				LibrarianDao libData = new LibrarianDao();
				Librarian lib = libData.getLibrarianData(Integer.parseInt(auth));
				request.setAttribute("userData", lib);
			}
			
			request.setAttribute("type", userType);

			rd = request.getRequestDispatcher("dashboard.jsp");
		} else {
			request.setAttribute("loginFail", auth);
			rd = request.getRequestDispatcher("index.jsp");
		}
		
		
		// To call JSP page that will display the data either using dispatcher or send redirect.
		rd.forward(request, response);
	}

}
