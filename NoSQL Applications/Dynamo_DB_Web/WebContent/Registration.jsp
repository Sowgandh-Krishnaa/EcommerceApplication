<html>
	<body> 
		<div style="text-align: center;">
		<div style="box-sizing: border-box; display: inline-block; width: auto; max-width: 480px; background-color: white; border: 2px; box-shadow: 0px 0px 8px blue; margin: 50px auto">
		<div style="background: DodgerBlue ; border-radius: 5px 5px; padding: 15px;"><span style="font-family: verdana,arial; color: white; font-size: 1.00em; font-weight:bold;">Registration Form</span></div>
		<div style="padding: 15px">
		<style type="text/css">
			table.center{margin-left:auto; margin-right:auto;}
		</style>
		<h1>Registration</h1>
		<form method="post" action="http://localhost:8080/Dynamo_DB_Web/Registration_Servlet" name="aform" target="_top">
			<input type="hidden" name="action" value="login">
			<input type="hidden" name="hide" value="">
			<table class='center'>
				<tr><td>First name:</td><td width="300"><input type="text" name="firstname"></td></tr>
				<tr><td>Last name:</td><td><input type="text" name="lastname"></td></tr>
				<tr><td>email-id:</td><td><input type="text" name="emailid"></td></tr>
				<tr><td>user-id:</td><td><input type="text" name="userid"></td></tr>
				<tr><td>password:</td><td><input type="password" name="password"></td></tr>
			</table>
			<input type="submit" value="Submit">
		</form>
		</div></div></div>
	</body>
</html>