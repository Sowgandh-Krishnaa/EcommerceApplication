<html>
	<body> 
		<div style="text-align: center;">
		<div style="box-sizing: border-box; display: inline-block; width: auto; max-width: 480px; background-color: white; border: 2px; box-shadow: 0px 0px 8px blue; margin: 50px auto">
		<div style="background: DodgerBlue ; border-radius: 5px 5px; padding: 15px;"><span style="font-family: verdana,arial; color: white; font-size: 1.00em; font-weight:bold;">Enter your Login and Password</span></div>
		<div style="padding: 15px">
		<style type="text/css">
			table.center{margin-left:auto; margin-right:auto;}
		</style> 
		<h1> Welcome </h1>
		<form method="post" action="http://localhost:8080/Dynamo_DB_Web/SimpleServlet" name="aform" target="_top">
			<input type="hidden" name="action" value="login">
			<input type="hidden" name="hide" value="">
			<table class='center'>
				<h2>Login unsuccessful.</h2>
				<tr><td>Username:</td><td><input type="text" name="Login"></td></tr>
				<tr><td>Password:</td><td><input type="password" name="password"></td></tr>
				<tr><td>&nbsp;</td><td><input type="submit" value="Submit"></td></tr>
				<tr><td colspan=2>&nbsp;</td></tr>
				<tr><td colspan=2>Not member yet? Click <a href="http://localhost:8080/Dynamo_DB_Web/Registration.jsp">here</a> to register.</td></tr>
			</table>
		</form>
		</div></div></div>
	</body>
</html>