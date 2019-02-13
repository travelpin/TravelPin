package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        //Primitive design of registration only include userId, email addr and password
	        String userId = request.getParameter("userId");
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        try{
	        
	        	//Load drivers for MySQL
	        	Class.forName("com.mysql.jdbc.Driver");

	        	//Create connection with the database 
	        	Connection con=DriverManager.getConnection("jdbc:mysql:/ /localhost:3306/test","userId","password");

	        	PreparedStatement ps=con.prepareStatement("insert into AuthUserList values(?,?,?)");

	        	ps.setString(1, userId);
	        	ps.setString(2, password);
	        	ps.setString(3, email);
	        
	        	int i=ps.executeUpdate();
	        
	        	if(i>0){
	        		out.println("User registered!");
	        	}
	        }
	        catch(Exception se)
	        {
	            se.printStackTrace();
	        }
	}

}
