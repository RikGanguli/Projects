<%@ page import="com.libapp.model.Member"%>
<%@ page import="com.libapp.model.Librarian"%>
<%@ page import="com.libapp.model.Document"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modify Document</title>
</head>
<body>
	<% 
		String userType = (String) request.getAttribute("type");
		Member memData = new Member();
		Librarian libData = new Librarian();
		if(userType.equals("Member")) {
			memData = (Member)request.getAttribute("userData");
		} else {
			libData = (Librarian)request.getAttribute("userData");
		}
	%>
	<form id="home" action="home" method="post">
		<% if(userType.equals("Member")) { %>
	    		<input type="hidden" value="<%=memData%>" name="userData" />
	    <%	} else { %>
	    		<input type="hidden" value="<%=libData%>" name="userData" />
	    <%	} %>
		<input type="hidden" value="<%=userType%>" name="type" />
		<a href="javascript:{}" onclick="document.getElementById('home').submit();">Dashboard</a>
	</form>
	
	<form id="logout" action="logout" method="get">
		<a href="javascript:{}" onclick="document.getElementById('logout').submit();">Log out</a>
	</form>
	
	<%
		Document docData = (Document) request.getAttribute("docData");
	%>
	
	<form action="searchFunctions" method="get">
		<input type="hidden" value="<%=userType%>" name="type" />
		<input type="hidden" value="<%=docData%>" name="borrowedDoc" />
		<input type="hidden" value="<%=libData%>" name="userData" />
	
		<label for="title"><b>Title</b></label>
	    <input type="text" value="<%=docData.getTitle()%>" name="title" required />
	    
	    <label for="author"><b>Author</b></label>
	    <input type="text" value="<%=docData.getAuthor()%>" name="author" required />
	    
	    <label for="genre"><b>Genre</b></label>
	    <input type="text" value="<%=docData.getGenre()%>" name="genre" required />
	
	    <label for="publisher"><b>Publisher</b></label>
	    <input type="text" value="<%=docData.getPublisher()%>" name="publisher" required />
	    
	    <label for="year"><b>Publish Year</b></label>
	    <input type="text" pattern="[0-9]{4}" value="<%=docData.getPublishYear()%>" name="year" required />
	
	    <label for="edition"><b>Edition</b></label>
	    <input type="text" pattern="[0-9]+" value="<%=docData.getEdition()%>" name="edition" required />
	    
	    <label for="issueno"><b>Issue Number</b></label>
	    <input type="text" pattern="[0-9]+" value="<%=docData.getIssueNo()%>" name="issueno" />
	
	    <label for="issuedate"><b>Issue Date</b></label>
	    <input type="date" value="<%=docData.getIssueDate()%>" name="issuedate" />
	    
	    <label for="editor"><b>Editor</b></label>
	    <input type="text" value="<%=docData.getEditor()%>" name="editor" />
	
	    <label for="docname"><b>Document Name</b></label>
	    <input type="text" value="<%=docData.getDocName()%>" name="docname" />
	    
	    <label for="doctype"><b>Document Type</b></label>
	    <select name="doctype" required>
	    	<option value="Book">Book</option>
		 	<option value="Journal Article">Journal Article</option>
		 	<option value="Magazine">Magazine</option>
		 	<option value="Thesis">Thesis</option>
		 	<option value="Technical Report">Technical Report</option>
		</select>

		<button type="submit" name="button" value="updateinfo">Update</button>
	</form>
	
</body>
</html>