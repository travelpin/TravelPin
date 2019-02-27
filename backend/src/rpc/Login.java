package rpc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DBConnection connection = DBConnectionFactory.getConnection();
        // try {
        // 	HttpSession session = request.getSession(false);
        // 	JSONObject obj = new JSONObject();

        // 	if (session != null) {
        // 		String user_Id = session.getAttribute("user").toString();
        // 		obj.put("status", "OK").put("user_id", user_Id).put("name", connection.getFullname(user_Id));
        // 	} else {
        // 		response.setStatus(403);
        // 		obj.put("status", "Invalid Session");
        // 	}
        // 	//RpcHelper.writeJsonObject(response, obj);
        // } catch (Exception e) {
        // 	e.printStackTrace();
        // } finally {
        // 	connection.close();
        // }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection connection = DBConnectionFactory.getConnection();
        JSONObject input = RpcHelper.readJSONObject(request);


        // Connect to mysql and verify username password

        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            // loads driver
//            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/TravelPin", "nopassword", ""); // gets a new connection

            //Convert username to user_Id for internal use.
//            String user_Id=username;
//
//            PreparedStatement ps = c.prepareStatement("select user_Id,password from users where user_Id=? and password=?");
//            ps.setString(1, user_Id);
//            ps.setString(2, password);
//
//            //Check if username and pw match
//            ResultSet rs = ps.executeQuery();
            String username = input.getString("username");
            String password = input.getString("password");

            //Convert user entered username into user_Id that DB recognize
            String user_Id=username;
            boolean isLoggedIn = connection.verifyLogin(user_Id, password);
            JSONObject responseObj = new JSONObject();
            if(isLoggedIn) {
                response.setStatus(200);
                responseObj.put("status", "OK").put("user", user_Id);
            }else{
                response.setStatus(403);
                responseObj.put("status", "User Doesn't Exist");
            }

            RpcHelper.writeJsonObject(response, responseObj);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            connection.close();
        }

    }



}
