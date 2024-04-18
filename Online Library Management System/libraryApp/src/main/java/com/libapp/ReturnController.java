package com.libapp;

import java.io.IOException;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.libapp.dao.DocumentDao;
import com.libapp.dao.MemberDao;
import com.libapp.model.Document;
import com.libapp.model.Librarian;
import com.libapp.model.Member;

import jakarta.servlet.RequestDispatcher;

/**
 * Servlet implementation class ReturnController
 */
public class ReturnController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userType = request.getParameter("type").toString();
		String action = request.getParameter("button").toString();
		String userData = request.getParameter("userData").toString();
		
//		System.out.println("asddfghjhgbfvds: " + action);
		
		DocumentDao docDao = new DocumentDao();
		RequestDispatcher rd;
		
		if(userType.equals("Member")) {
			Member memData = new Member(userData);
			if(action.equals("return")) {
				MemberDao memDao = new MemberDao();
				memData = memDao.updateMember(memData, memData.getDid(), "r");
				
				docDao.updateDocRecord(memData.getDid(), "r");
			}
			request.setAttribute("userData", memData);
		} else {
			Librarian libData = new Librarian(userData);
			if(action.equals("addDoc")) {
				request.setAttribute("userData", libData);
				request.setAttribute("type", userType);
				rd = request.getRequestDispatcher("add.jsp");
				rd.forward(request, response);
			} else if(action.equals("add")) {
				Document doc = new Document();
				String title = request.getParameter("title") != null ? request.getParameter("title").toString() : "null";
				String author = request.getParameter("author") != null ? request.getParameter("author").toString() : "null";
				String genre = request.getParameter("genre") != null ? request.getParameter("genre").toString() : "null";
				String publish = request.getParameter("publisher") != null ? request.getParameter("publisher").toString() : "null";
				String year = request.getParameter("year") != null ? request.getParameter("year").toString() : "null";
				String edition = request.getParameter("edition") != null ? request.getParameter("edition").toString() : "null";
				String issueno = request.getParameter("issueno") != null ? request.getParameter("issueno").toString() : "null";
				String issuedate = request.getParameter("issuedate") != null ? request.getParameter("issuedate").toString() : "null";
				String editor = request.getParameter("editor") != null ? request.getParameter("editor").toString() : "null";
				String docname = request.getParameter("docname") != null ? request.getParameter("docname").toString() : "null";
				String doctype = request.getParameter("doctype") != null ? request.getParameter("doctype").toString() : "null";
				String total = request.getParameter("total") != null ? request.getParameter("total").toString() : "null";

				doc.setTitle(!title.equals("null") && !title.equals("") ? title : null);
				doc.setAuthor(!author.equals("null") && !author.equals("") ? author : null);
				doc.setGenre(!genre.equals("null") && !genre.equals("") ? genre : null);
				doc.setPublisher(!publish.equals("null") && !publish.equals("") ? publish : null);
				doc.setPublishYear(!year.equals("null") && !year.equals("") ? Integer.parseInt(year) : null);
				doc.setEdition(!edition.equals("null") && !edition.equals("") ? Integer.parseInt(edition) : null);
				doc.setIssueNo(!issueno.equals("null") && !issueno.equals("") ? Integer.parseInt(issueno) : null);
				doc.setIssueDate(!issuedate.equals("null") && !issuedate.equals("") ? Date.valueOf(issuedate) : null);
				doc.setEditor(!editor.equals("null") && !editor.equals("") ? editor : null);
				doc.setDocName(!docname.equals("null") && !docname.equals("") ? docname : null);
				doc.setDocType(doctype);
				doc.setTotal(!total.equals("null") && !total.equals("") ? Integer.parseInt(total) : null);

				docDao.addDoc(doc, libData.getLid());
			}
			request.setAttribute("userData", libData);
		}
		request.setAttribute("type", userType);
		rd = request.getRequestDispatcher("dashboard.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
