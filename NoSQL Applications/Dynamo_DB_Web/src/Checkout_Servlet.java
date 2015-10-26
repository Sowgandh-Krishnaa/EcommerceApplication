

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Checkout_Servlet
 */
public class Checkout_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Checkout_Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	// Gets the price of the input product
	public static String getPrice(String prodDetails){

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
		String price = prodDetails.substring(7,prodDetails.indexOf(","));
		return price; 

	}
	
	// Splits the input product details.
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

		// TODO Auto-generated method stub
		
		// Quantities of each item being requested for.
		int macqty = Integer.parseInt(request.getParameter("macqty")); 
		int watchqty = Integer.parseInt(request.getParameter("watchqty"));
		int ballqty = Integer.parseInt(request.getParameter("ballqty"));

		AmazonDynamoDB amObject = new AmazonDynamoDB();
		try {
			amObject.init();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// 0 - watch  , 1 - mac, 2 - ball
		int[] availableQty = amObject.checkItems(watchqty, macqty, ballqty); 
		if(availableQty[0] == 1 && availableQty[1] == 1 && availableQty[2] == 1){
			String macDesc = amObject.itemDescription("568");
			int macPrice = Integer.parseInt(getPrice(macDesc));

			String watchDesc = amObject.itemDescription("876");
			int watchPrice = Integer.parseInt(getPrice(watchDesc));

			String ballDesc = amObject.itemDescription("356");
			int ballPrice = Integer.parseInt(getPrice(ballDesc));

			int totalMacPrice = macqty * macPrice ;
			int totalWatchPrice = watchqty * watchPrice;
			int totalBallPrice = ballqty * ballPrice;

			String totalCost = totalMacPrice + totalWatchPrice + totalBallPrice +  "";

			request.setAttribute("totalMacPrice", totalMacPrice);
			request.setAttribute("totalWatchPrice", totalWatchPrice);
			request.setAttribute("totalBallPrice", totalBallPrice);
			request.setAttribute("totalCost", totalCost); 
			// Displays list of items being purchased, their price and the total cost.
			request.getRequestDispatcher("Checkout.jsp").forward(request, response); 
		} else { // Returns the item list page with error statement about unavailability of requested quantity.
			String noWatches ="";
			String noBalls = "";
			String noMacs = "";
			
			if(availableQty[0] == 0){
				noWatches = "Titan watches,";
				request.setAttribute("noWatches", noWatches);
			}
			if(availableQty[1] == 0){
				noMacs = "Mac Book Pros,";
				request.setAttribute("noMacs", noMacs);
			}
			if(availableQty[2] == 0){
				noBalls = "Footballs,";
				request.setAttribute("noBalls", noBalls);
			}
			
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
			
			request.getRequestDispatcher("ItemListUnavail.jsp").forward(request, response);
		}

	}
}


