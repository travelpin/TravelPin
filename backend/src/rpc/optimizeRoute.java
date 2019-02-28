package rpc;



import algorithm.algorithm;
import db.DBConnection;
import db.DBConnectionFactory;
import entity.Interest;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/optimizeroute")
public class optimizeRoute extends HttpServlet {

    public optimizeRoute(){
        super();
    }
    private Connection conn;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection connection = DBConnectionFactory.getConnection();
        try{

            JSONObject inputJSON = RpcHelper.readJSONObject(request);
            int days = inputJSON.getInt("days");
            JSONArray pinnedInterestsJSONArray = inputJSON.getJSONArray("pinnedInterests");
            algorithm algorithmCollection = new algorithm();
            List<Interest> pinnedInterests = connection.getInterestsByLocationId(pinnedInterestsJSONArray);
            System.out.println(pinnedInterests);
            List<List<Interest>> interestArrangements = algorithmCollection.optimizeRoute(pinnedInterests, days);
            System.out.println(interestArrangements);
            JSONArray arrangements = new JSONArray();
            for(List<Interest> interests : interestArrangements){
                JSONArray arrangement = new JSONArray();
                for(Interest interest: interests){
                    arrangement.put(interest.toJSONObject());
                }
                arrangements.put(arrangement);
                arrangement = null;
            }
            RpcHelper.writeJsonArray(response, arrangements);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
