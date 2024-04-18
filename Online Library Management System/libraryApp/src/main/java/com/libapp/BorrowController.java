package com.libapp;

import java.io.IOException;
import java.sql.Date;

import com.libapp.dao.DocumentDao;
import com.libapp.dao.MemberDao;
import com.libapp.model.Document;
import com.libapp.model.Librarian;
import com.libapp.model.Member;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 * Servlet implementation class BorrowController
 */
public class BorrowController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowController() {
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
		String borrowedDoc = request.getParameter("borrowedDoc").toString();
		
//		System.out.println(userData);
		
		Document doc = new Document(borrowedDoc);
		
		int did = doc.getDid();
		DocumentDao docDao = new DocumentDao();
		
		RequestDispatcher rd = null;
		
		if(userType.equals("Member")) {
			Member memData = new Member(userData);
			
			if(action.equals("borrow")) {
				if(memData.getReturnDate() != null) {
					MemberDao memDao = new MemberDao();
					memData = memDao.updateMember(memData, did, "b");
					
					docDao.updateDocRecord(did, "b");
				} else {
					request.setAttribute("borrowFail", "Please return existing borrowed document before borrowing another document.");
					request.setAttribute("userData", memData);
					request.setAttribute("type", userType);
					rd = request.getRequestDispatcher("borrowFail.jsp");
					rd.forward(request, response);
				}
			}
			request.setAttribute("userData", memData);
		} else {
			Librarian libData = new Librarian(userData);
			
//			System.out.println("----dsff-----------" + did);
			
			if(action.equals("addCopy")) {
				docDao.updateDocRecord(did, "a");
			} else if(action.equals("delCopy")) {
				docDao.updateDocRecord(did, "d");
			} else if(action.equals("modifyInfo")) {
				request.setAttribute("docData", doc);
				request.setAttribute("userData", libData);
				request.setAttribute("type", userType);
				rd = request.getRequestDispatcher("modify.jsp");
				rd.forward(request, response);
			} else if(action.equals("updateinfo")) {
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
				System.out.println("dsfdgfdfgh  " + year);
				System.out.println("dsfdgfdfgh  " + Integer.parseInt(year));
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
				System.out.println("dsfdgfdfgh  " + doc.getPublishYear());
				docDao.updateDoc(doc, libData.getLid());
			} else if(action.equals("delDoc")) {
				docDao.deleteDoc(did);
			}
			request.setAttribute("userData", libData);
		}
		
		rd = request.getRequestDispatcher("dashboard.jsp");
		request.setAttribute("type", userType);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userType = request.getParameter("type").toString();
		String mem = request.getParameter("userData").toString();
		System.out.println(userType);
		Member memData = new Member(mem);
		
		request.setAttribute("userData", memData);
		request.setAttribute("type", userType);
		RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
		rd.forward(request, response);
	}

}
