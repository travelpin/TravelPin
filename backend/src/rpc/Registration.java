package rpc;

import db.DBConnection;
import db.DBConnectionFactory;
import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/signup")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: registration").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection connection = DBConnectionFactory.getConnection();
		JSONObject input = RpcHelper.readJSONObject(request);
		try {
			String username = input.getString("username");
			String password = input.getString("password");
			String firstName = "shouldbeNull";
			String lastName = "shouldbeNull";
			System.out.println("username is : "+username+". password is : "+password);
			boolean userDidRegister = connection.registration(username, password, firstName, lastName);
			JSONObject obj = new JSONObject();
			if (userDidRegister) {
				obj.put("User registration ", "succeeded");
				obj.put("username", username);
				obj.put("password", password);
				obj.put("first_name", firstName);
				obj.put("last_name", lastName);
			} else {
				obj.put("User registration ", "failed");
			}
			RpcHelper.writeJsonObject(response, obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

}
