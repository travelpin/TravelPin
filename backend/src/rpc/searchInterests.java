package rpc;

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
import java.util.List;


@WebServlet("/searchinterests")
public class searchInterests extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        DBConnection connection = DBConnectionFactory.getConnection();
        try {
            //get list of interests from database
            List<Interest> interests = connection.searchByName(name);
            JSONArray array = new JSONArray();
            for(Interest interest : interests){
                JSONObject obj = interest.toJSONObject();
                array.put(obj);
            }
            RpcHelper.writeJsonArray(response, array);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }
    }
}
//!!!!!Required functionalities not implemented :
//!!!!! Data Structure : Interest
//!!!!! DBConnection

//public List<Interest> searchByName(String name){
//    if(connection == null){
//        return new ArrayList<>();
//    }
//
//    List<Interest> matchingInterests = new ArrayList<>();
//    try{
//        String sql = "SELECT * FROM interests WHERE interest_id LIKE ?";//if interest name is the interestId in the database
//        //String sql = "SELECT * FROM interests WHERE name LIKE ?";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setString(1, "%" + name + "%");
//        ResultSet rs = stmt.executeQuery();
//
//        //put results in our data structure "Interest"
//        InterestBuilder builder = new InterestBuilder();
//        while(rs.next()){
//            builder.setId(rs.getString("interest_id"));
//            builder.setName(rs.getString("name"));
//            builder.setAddress(rs.getString("address"));
//            builder.setCategory(rs.getString("category"));
//
//            matchingInterests.add(builder.build());
//        }
//
//    }catch(SQLException e){
//        e.printStackTrace();
//    }
//    return matchingInterests;
//}