<%@ page import="com.libapp.model.Member"%>
<%@ page import="com.libapp.model.Librarian"%>
<%@ page import="com.libapp.model.Document"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>
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

	<!-- Print search results fetched from database here -->
	<%
		Document searchList[] = (Document[]) request.getAttribute("searchList");
		for(int i = 0; i < searchList.length; i++) {
			String disabled = "enabled";
			String deletable = "disabled";
	%>
			<form action="searchFunctions" method="get">
		  		<input type="hidden" value="<%=userType%>" name="type" />
		  		<input type="hidden" value="<%=searchList[i]%>" name="borrowedDoc" />
	<%			if(searchList[i].getRemaining() == 0) {
					disabled = "disabled";
				}
				if(searchList[i].getRemaining() == searchList[i].getTotal()) {
					deletable = "enabled";
				}
				if(userType.equals("Member")){
					out.println(searchList[i]);
	%>
					<input type="hidden" value="<%=memData%>" name="userData" />
					<button type="submit" name="button" value="borrow" <%=disabled%>>Borrow</button>
	<%			} else {
					out.println(searchList[i]);
	%>
					<input type="hidden" value="<%=libData%>" name="userData" />
					<button type="submit" name="button" value="addCopy">Add Copy</button>
					<button type="submit" name="button" value="delCopy" <%=disabled%>>Delete Copy</button>
					<button type="submit" name="button" value="modifyInfo">Modify Information</button>
					<button type="submit" name="button" value="delDoc" <%=deletable%>>Delete Document</button>
	<%
				}
	%>
			</form>
	<%	}
	%>
	
	<!-- Add a borrow button next to search results with both inside a form and then call for borrow -->
	<!-- Also grey out the borrow button if copies are over -->
	<!-- Show warning on borrow page if any books are overdue -->
	
</body>
</html>