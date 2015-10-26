<html>
	<body> 
		<div style="text-align: center;">
		<div style="box-sizing: border-box; display: inline-block; width: auto; max-width: 480px; background-color: white; border: 2px; box-shadow: 0px 0px 8px blue; margin: 50px auto">
		<div style="background: DodgerBlue ; border-radius: 5px 5px; padding: 15px;"><span style="font-family: verdana,arial; color: white; font-size: 1.00em; font-weight:bold;">Items We Sell</span></div>
		<div style="padding: 15px">
		<style type="text/css">
			table.center{margin-left:auto; margin-right:auto;}
		</style>
		<h1>Item List</h1>
		<font color="red">The requested quantity of ${noBalls} ${noWatches} ${noMacs} are unavailable.</font>
		<form method="post" action="http://localhost:8080/Dynamo_DB_Web/Checkout_Servlet" name="aform" target="_top">
			<table border="2">
				<tr><th>Item</th><th>Quantity</th><th>Description</th>
				<tr><td>Mac Book Pro</td><td>  
				<select name="macqty">
				<option value="0">0</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				
				</select> </td>
				<td colspan="10">
				${macDet}</br>${macDesc} 
				</td>
				</tr>
				
				<tr><td>Titan Watch   </td><td>
				<select name="watchqty">
				<option value="0">0</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				
				</select> </td>
				<td colspan="10">
				${watchDet}</br>${watchDesc} 
				</td></tr>
				
				<tr><td>Football     </td><td>
				<select name="ballqty">
				<option value="0">0</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				
				</select> </td>
				<td colspan="10">
				${ballDet}</br>${ballDesc} 
				</td></tr>
				
				<tr><td> </td></tr>
			</table>
		<input type="submit" value="Checkout" bgcolor=Green> 
		</form>
	</body>
</html>