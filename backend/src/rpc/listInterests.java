package rpc;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Interest;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;


@WebServlet("/listinterests")
public class listInterests extends HttpServlet {
    public listInterests(){
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection connection = DBConnectionFactory.getConnection();
        System.out.println("listInterests was called");
        try {
            Set<Interest> interests = connection.getAllInterests();
            JSONArray array = new JSONArray();
            for (Interest interest : interests) {
                array.put(interest.toJSONObject());
            }
            RpcHelper.writeJsonArray(response, array);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
