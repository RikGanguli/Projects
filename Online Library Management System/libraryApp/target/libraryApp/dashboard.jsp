<%@ page import="com.libapp.model.Member"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="com.libapp.model.Librarian"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
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
    	if(userType.equals("Member")) {
    		out.println(memData);
    		//Print Member stats Show stats of the user like no. of books borrowed, no. of books returned, days of membership, etc.
    		out.println("Welcome " + memData.getFirstname());
    		
    		if(memData.getReturnDate() == null) {
 				if(Date.valueOf(LocalDate.now()).after(memData.getDueDate())) {
    %>				
    				<b>Your currently borrowed document is overdue!</b>>
    <%			}
    %>
    			<form action="return" method="get">
	    			<input type="hidden" value="<%=memData%>" name="userData" />
	    			<input type="hidden" value="<%=userType%>" name="type" />
	    			<label for="returnButton"><b>Return Borrowed Document</b></label>
	    			<button type="submit" name="button" value="return">Return</button>
	    		</form>
    <%		} else{
    %>
    			<b>We can read you like a book! Feel free to borrow from our collection!</b>
    <%		}
    	} else {
    		out.println(libData);
    		out.println("Welcome " + libData.getFirstname());
    %>
    		<form action="addDocument" method="get">
    			<input type="hidden" value="<%=libData%>" name="userData" />
	    		<input type="hidden" value="<%=userType%>" name="type" />
    			<button type="submit" name="button" value="addDoc">Add New Document</button>
    		</form>
    <%	}
  	%>
  	
  	<form action="getSearch" method="get">
	  	<% if(userType.equals("Member")) { %>
	    		<input type="hidden" value="<%=memData%>" name="userData" />
	    <%	} else { %>
	    		<input type="hidden" value="<%=libData%>" name="userData" />
	    <%	} %>
  		<input type="hidden" value="<%=userType%>" name="type" />
  		<input type="text" placeholder="Name/Author" name="search" required>
  		<button type="submit">Search</button>
  	</form>
  			
</body>
</html>