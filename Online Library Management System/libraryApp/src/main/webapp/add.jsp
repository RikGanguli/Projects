<%@ page import="com.libapp.model.Member"%>
<%@ page import="com.libapp.model.Librarian"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add New Document</title>
</head>
<body>
	<% 
		String userType = (String) request.getAttribute("type");
		Librarian libData = new Librarian();
		libData = (Librarian)request.getAttribute("userData");
	%>
	<form id="home" action="home" method="post">
	    <input type="hidden" value="<%=libData%>" name="userData" />

		<input type="hidden" value="<%=userType%>" name="type" />
		<a href="javascript:{}" onclick="document.getElementById('home').submit();">Dashboard</a>
	</form>
	
	<form id="logout" action="logout" method="get">
		<a href="javascript:{}" onclick="document.getElementById('logout').submit();">Log out</a>
	</form>
	
	<form action="addDocument" method="get">
		<input type="hidden" value="<%=userType%>" name="type" />
		<input type="hidden" value="<%=libData%>" name="userData" />
	
		<label for="title"><b>Title</b></label>
	    <input type="text" name="title" required />
	    
	    <label for="author"><b>Author</b></label>
	    <input type="text" name="author" required />
	    
	    <label for="genre"><b>Genre</b></label>
	    <input type="text" name="genre" required />
	
	    <label for="publisher"><b>Publisher</b></label>
	    <input type="text" name="publisher" required />
	    
	    <label for="year"><b>Publish Year</b></label>
	    <input type="text" pattern="[0-9]{4}" name="year" required />
	
	    <label for="edition"><b>Edition</b></label>
	    <input type="text" pattern="[0-9]+" name="edition" required />
	    
	    <label for="issueno"><b>Issue Number</b></label>
	    <input type="text" pattern="[0-9]+" name="issueno" />
	
	    <label for="issuedate"><b>Issue Date</b></label>
	    <input type="date" name="issuedate" />
	    
	    <label for="editor"><b>Editor</b></label>
	    <input type="text" name="editor" />
	
	    <label for="docname"><b>Document Name</b></label>
	    <input type="text" name="docname" />
	    
	    <label for="total"><b>Copies</b></label>
	    <input type="text" pattern="[0-9]+" name="total" required />
	    
	    <label for="doctype"><b>Document Type</b></label>
	    <select name="doctype" required>
	    	<option value="Book">Book</option>
		 	<option value="Journal Article">Journal Article</option>
		 	<option value="Magazine">Magazine</option>
		 	<option value="Thesis">Thesis</option>
		 	<option value="Technical Report">Technical Report</option>
		</select>

		<button type="submit" name="button" value="add">Add</button>
	</form>
</body>
</html>