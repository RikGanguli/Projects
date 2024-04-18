<html>
<body>
<% String failAuth = (String) request.getAttribute("loginFail");
if(failAuth == null) { %>
	<form action="getLogin" method="post">
	  <!-- div class="imgcontainer">
	    <img src="img_avatar2.png" alt="Avatar" class="avatar">
	  </div -->
	
	  <div class="container">
	    <label for="user"><b>Username</b></label>
	    <input type="text" pattern="[0-9]+" placeholder="Enter Username" name="user" required>
	
	    <label for="pwd"><b>Password</b></label>
	    <input type="password" placeholder="Enter Password" name="pwd" required>
	    
	    <label for="type"><b>User Type</b></label>
	    <select name="type" required>
		  <option value="Member">Member</option>
		  <option value="Librarian">Librarian</option>
		</select>
	
	    <button type="submit">Login</button>
	    <!-- label>
	      <input type="checkbox" checked="checked" name="remember"> Remember me
	    </label -->
	  </div>
	
	  <!-- div class="container" style="background-color:#f1f1f1">
	    <button type="button" class="cancelbtn">Cancel</button>
	    <span class="psw">Forgot <a href="#">password?</a></span>
	  </div -->
	</form>

<% } else { %>
	<form action="failAck" method="get">
		<b>
			<% 
				out.println(failAuth);
			%>
		</b>
		<button type="submit">OK</button>
	</form>
<% } %>
</body>
</html>
