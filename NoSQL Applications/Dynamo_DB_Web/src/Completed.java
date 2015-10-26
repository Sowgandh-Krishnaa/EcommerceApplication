

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Completed
 */
public class Completed extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Completed() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	// Splits the input product description.
	public static String split(String prodDetails){
		
		prodDetails = prodDetails.replace("{", "");
		prodDetails = prodDetails.replace("}", "");
		prodDetails = prodDetails.replace("[", "");
		prodDetails = prodDetails.replace("]", "");
		prodDetails = prodDetails.replace(",,", ",");
		int startOfProdDesc = prodDetails.indexOf("productDescription");
		int endOfProdDesc = prodDetails.indexOf("productName");
		String prodDesc = prodDetails.substring(startOfProdDesc,endOfProdDesc);
		prodDetails = prodDetails.replace(prodDesc, "");
		prodDetails = prodDetails.replace("S:", "");
		prodDesc = prodDesc.replace("S:", "");
		prodDesc = prodDesc.replace(",", ", ");
		prodDetails = prodDetails.substring(0,prodDetails.length()-1);
		prodDesc = prodDesc.substring(0,prodDesc.length()-3);
		return prodDetails + "----" + prodDesc;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AmazonDynamoDB amObject = new AmazonDynamoDB();
		try {
			amObject.init();
		} catch (Exception e) {
			e.printStackTrace();
		} 

		// Check if user has performed Cancel or Buy Items operation.
		if(request.getParameter("commonName").equals("Cancel")){
			String macDesc = amObject.itemDescription("568");
			String properDetailsMac = split(macDesc);
			String[] detailsMac = properDetailsMac.split("----"); 
			
			request.setAttribute("macDet", detailsMac[0]);
			request.setAttribute("macDesc", detailsMac[1]);
			
			String watchDesc = amObject.itemDescription("876");
			String properDetailsWatch = split(watchDesc);
			String[] detailsWatch = properDetailsWatch.split("----"); 
			
			request.setAttribute("watchDet", detailsWatch[0]);
			request.setAttribute("watchDesc", detailsWatch[1]);
			
			String ballDesc = amObject.itemDescription("356");
			String properDetailsBall = split(ballDesc);
			String[] detailsBall = properDetailsBall.split("----"); 
			
			request.setAttribute("ballDet", detailsBall[0]);
			request.setAttribute("ballDesc", detailsBall[1]);
			// Displays the item list page once again.
			request.getRequestDispatcher("ItemList.jsp").forward(request, response); 

		} else{
			amObject.updateDatabase();
			// Completes the transaction and displays success page.
			request.getRequestDispatcher("CompletedTxn.jsp").forward(request, response);
		}
	}

}
