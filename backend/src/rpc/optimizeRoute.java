package rpc;



import algorithm.algorithm;
import entity.Interest;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "optimizeRoute")
public class optimizeRoute extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject inputJSON = RpcHelper.readJSONObject(request);
        int days = inputJSON.getInt("days");
        List<Interest> pinnedInterests = new ArrayList<>();
        JSONArray pinnedInterestsJSONArray = inputJSON.getJSONArray("pinnedInterests");
        for(int i = 0; i<pinnedInterestsJSONArray.length(); i++){
            JSONObject tempObject = (JSONObject) pinnedInterestsJSONArray.get(i);
            String pinnedInteretsName = tempObject.getString("name");
            Interest tempInterest = RpcHelper.interestName_to_Interest(pinnedInteretsName);
            pinnedInterests.add(tempInterest);
        }
        algorithm algorithmCollection = new algorithm();
        try{
            List<List<Interest>> interestArrangements = algorithmCollection.optimizeRoute(pinnedInterests, days);
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
