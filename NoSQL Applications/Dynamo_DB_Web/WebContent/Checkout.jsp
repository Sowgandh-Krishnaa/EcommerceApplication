<html>
	<body> 
		<div style="text-align: center;">
		<div style="box-sizing: border-box; display: inline-block; width: auto; max-width: 480px; background-color: white; border: 2px; box-shadow: 0px 0px 8px blue; margin: 50px auto">
		<div style="background: DodgerBlue ; border-radius: 5px 5px; padding: 15px;"><span style="font-family: verdana,arial; color: white; font-size: 1.00em; font-weight:bold;">Items In Cart</span></div>
		<div style="padding: 15px">
		<style type="text/css">
			table.center{margin-left:auto; margin-right:auto;}
		</style> 
		<h1>Cart List</h1>
		<form method="post" action="http://localhost:8080/Dynamo_DB_Web/Completed" name="aform" target="_top">
		<table border="1">
		  <thead>
		    <tr>
		      <th>Item</th>
		      <th>Total</th>
		    </tr>
		  </thead>
		<tr><td>Mac Book Pro  </td><td> ${totalMacPrice}</td></tr>
		<tr><td>Titan Watch   </td><td> ${totalWatchPrice}</td></tr>
		<tr><td>Football     </td><td> ${totalBallPrice}</td></tr>
		<tr><td> </td><td> </td></tr>
		<tr><td>TOTAL COST     </td><td> ${totalCost}</td></tr>
		<tr><td> </td></tr>
		
		</table>
		<table>
		<tr><td>
		<input type="submit" value="Cancel" name="commonName"> </td><td>
		<input type="submit" value="Buy Items" name="commonName">  
		</td></tr>
		</table>
		</form>
	</body>
</html>