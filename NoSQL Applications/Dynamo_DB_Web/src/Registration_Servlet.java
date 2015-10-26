

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registration_Servlet
 */
public class Registration_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	String initialitems = "{568:0,876:0,356:0}";

	public Registration_Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email_id = request.getParameter("emailid");
		String user_id = request.getParameter("userid");
		String password = request.getParameter("password");

		AmazonDynamoDB amObject = new AmazonDynamoDB();
		try {
			amObject.setName(user_id); 
			amObject.init();
			//Registers the user in the database.
			amObject.registerUser(user_id, password, firstname, lastname, email_id,initialitems);
		} catch (Exception e){
			e.printStackTrace();
		}
		// Displays the successful registration page.
		request.getRequestDispatcher("/RegSuccess.jsp").include(request, response);
		request.getRequestDispatcher("/RegSuccess.jsp").forward(request, response);
	}
}
