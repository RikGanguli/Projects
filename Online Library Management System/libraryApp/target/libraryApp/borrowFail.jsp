<%@ page import="com.libapp.model.Member"%>
<%@ page import="com.libapp.model.Librarian"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Unable To Borrow</title>
</head>
<body>
	<% 
		String userType = (String) request.getAttribute("type");
		Member memData = new Member();
		memData = (Member)request.getAttribute("userData");
		String borrowFail = (String) request.getAttribute("borrowFail");
	%>
	
	<form action="borrowFailAck" method="post">
		<b>
			<% 
				out.println(borrowFail);
			%>
		</b>
		<input type="hidden" value="<%=memData%>" name="userData" />
		<input type="hidden" value="<%=userType%>" name="type" />
		<button type="submit">OK</button>
	</form>
</body>
</html>