package backend.src.rpc;

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


import backend.src.db.DBConnection;
import backend.src.db.DBConnectionFactory;
import org.json.JSONObject;


/**
 * Servlet implementation class Login
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
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

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Connect to mysql and verify username password

        try {
            Class.forName("com.mysql.jdbc.Driver");
            // loads driver
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root"); // gets a new connection

            //Convert username to user_Id for internal use.
            String user_Id=username;

            PreparedStatement ps = c.prepareStatement("select user_Id,password from users where user_Id=? and password=?");
            ps.setString(1, user_Id);
            ps.setString(2, password);

            //Check if username and pw match
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //Can be implemented in another way, for now just a success HTML returned
                response.sendRedirect("success.html");
                return;
            }
            response.sendRedirect("error.html");
            return;
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            connection.close();
        }

    }



}
