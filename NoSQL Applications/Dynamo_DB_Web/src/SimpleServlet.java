

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
/**
 * Servlet implementation class SimpleServlet
 */
public class SimpleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	static int index = 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimpleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		String msg_1 = request.getParameter("Login");
		String msg_2 = request.getParameter("password");
		AmazonDynamoDB x = new AmazonDynamoDB();
		try {
			x.setName(msg_1);
		    x.init(); 
			boolean check = x.validateLogin(msg_1,msg_2); // Validates login details.
			if(check==true){ // Retrieves page with item list.
				String macDesc = x.itemDescription("568");
				String properDetailsMac = split(macDesc);
				String[] detailsMac = properDetailsMac.split("----"); 
				
				request.setAttribute("macDet", detailsMac[0]);
				request.setAttribute("macDesc", detailsMac[1]);
				
				String watchDesc = x.itemDescription("876");
				String properDetailsWatch = split(watchDesc);
				String[] detailsWatch = properDetailsWatch.split("----"); 
				
				request.setAttribute("watchDet", detailsWatch[0]);
				request.setAttribute("watchDesc", detailsWatch[1]);
				
				String ballDesc = x.itemDescription("356");
				String properDetailsBall = split(ballDesc);
				String[] detailsBall = properDetailsBall.split("----"); 
				
				request.setAttribute("ballDet", detailsBall[0]);
				request.setAttribute("ballDesc", detailsBall[1]);
				
				request.getRequestDispatcher("ItemList.jsp").forward(request, response);
			}else{ // Display login unsuccessful page
				request.getRequestDispatcher("/index_login_unsucessful.jsp").include(request, response);
				request.getRequestDispatcher("/index_login_unsucessful.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
