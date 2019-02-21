package backend.src.rpc;



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

        try{

            JSONObject inputJSON = RpcHelper.readJSONObject(request);
            int days = inputJSON.getInt("days");
            List<Interest> pinnedInterests = new ArrayList<>();
            JSONArray pinnedInterestsJSONArray = inputJSON.getJSONArray("pinnedInterests");
            algorithm algorithmCollection = new algorithm();

            for(int i = 0; i<pinnedInterestsJSONArray.length(); i++){
                JSONObject tempObject = (JSONObject) pinnedInterestsJSONArray.get(i);
                String location_id = tempObject.getString("interestsId");
                Interest.InterestBuilder builder = new Interest.InterestBuilder();
                String sql = "SELECT * FROM interests WHERE location_id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, location_id);
                ResultSet rs = statement.executeQuery();
                builder.setLocationId(rs.getString("lcoation_id"));
                builder.setName(rs.getString("name"));
                builder.setLat(rs.getDouble("lat"));
                builder.setLng(rs.getDouble("lng"));
                builder.setRating(rs.getDouble("rating"));
                builder.setOpenTime(rs.getDouble("open_time"));
                builder.setCloseTime(rs.getDouble("close_time"));
                builder.setSuggestVisitTime(rs.getDouble("suggest_visit_time"));
                builder.setFormattedAddress(rs.getString("formattedAddress"));
                builder.setPlaceId(rs.getString("placeId"));
                pinnedInterests.add(builder.build());
            }
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
