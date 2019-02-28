package db.mysql;

import java.sql.*;
import java.util.*;

import algorithm.algorithm;
import db.DBConnection;
import entity.Interest;
import entity.Interest.InterestBuilder;
import org.json.JSONArray;

import java.util.List;
import java.util.Set;

//import entity.Item;  // I am waiting for data object APIs

/*
Author: Debbie Liang
Date: Feb. 2019
 */

public class MySQLConnection implements DBConnection{

    private Connection conn;

    public MySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            conn = DriverManager.getConnection(MySQLDBUtil.URL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void setFavoriteInterests(String userId, List<String> locationIds) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "INSERT IGNORE INTO history(user_id, location_id) VALUE (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            for (String locationId : locationIds) {
                ps.setString(2, locationId);
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void unsetFavoriteInterests(String userId, List<String> locationIds) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "DELETE FROM history WHERE user_id = ? AND location_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            for (String locationId : locationIds) {
                ps.setString(2, locationId);
                ps.execute();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public void setFavoritePlans(String user_id, List<String> liked_plans) {
//        if (conn == null) {
//            System.err.println("DB connection failed");
//            return;
//        }
//
//        try {
//            String sql = "INSERT IGNORE INTO users(user_id, liked_plan) VALUES (?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, user_id);
//            for (String liked_plan : liked_plans) {
//                ps.setString(2, liked_plan);
//                ps.execute();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void unsetFavoritePlans(String user_id, List<String> liked_plans) {
//        if (conn == null) {
//            System.err.println("DB connection failed");
//            return;
//        }
//
//        try {
//            String sql = "DELETE FROM users WHERE user_id = ? AND liked_plan = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, user_id);
//            for (String liked_plan : liked_plans) {
//                ps.setString(2, liked_plan);
//                ps.execute();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public Set<Interest> getFavoriteInterests(String userId) {
        if (conn == null) {
            return new HashSet<>();
        }

        Set<Interest> favoriteInterests = new HashSet<>();
        Set<String> interestIds = getFavoriteInterestIds(userId);

        try {
            String sql = "SELECT * FROM interests WHERE location_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (String interestId : interestIds) {
                stmt.setString(1, interestId);

                ResultSet rs = stmt.executeQuery();

                InterestBuilder builder = new InterestBuilder();

                while(rs.next()) {
                    builder.setLocationId(rs.getString("location_id"));
                    builder.setName(rs.getString("name"));
                    builder.setLat(rs.getDouble("lat"));
                    builder.setLng(rs.getDouble("lng"));
                    builder.setRating(rs.getDouble("rating"));
                    builder.setOpenTime(rs.getDouble("open_time"));
                    builder.setCloseTime(rs.getDouble("close_time"));
                    builder.setSuggestVisitTime(rs.getDouble("suggest_visit_time"));
                    builder.setFormattedAddress(rs.getString("formattedAddress"));
                    builder.setPrice(rs.getDouble("price"));
                    builder.setImageUrl(rs.getString("imageUrl"));
                    builder.setPlaceId(rs.getString("placeId"));
                    builder.setCategories(getCategories(rs.getString("location_id")));

                    favoriteInterests.add(builder.build());

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favoriteInterests;
    }

    @Override
    public Set<String> getFavoriteInterestIds(String userId) {
        if (conn == null) {
            return new HashSet<>();
        }

        Set<String> favoriteInterestIds = new HashSet<>();

        try {
            String sql = "SELECT location_id FROM history WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String interestId = rs.getString("location_id");
                favoriteInterestIds.add(interestId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoriteInterestIds;
    }

    @Override
    public Set<Interest> getAllInterests() {
        if (conn == null) {
            return new HashSet<>();
        }

        Set<String> allInterestIds = new HashSet<>();
        try {
            String sql = "SELECT location_id FROM interests";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allInterestIds.add(rs.getString("location_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Set<Interest> allInterests = new HashSet<>();

        try {
            String sql = "SELECT * FROM interests WHERE location_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            for (String location_id: allInterestIds) {
                statement.setString(1, location_id);
                ResultSet rs = statement.executeQuery();
                InterestBuilder builder = new InterestBuilder();

                while (rs.next()) {
                    builder.setLocationId(rs.getString("location_id"));
                    builder.setName(rs.getString("name"));
                    builder.setLat(rs.getDouble("lat"));
                    builder.setLng(rs.getDouble("lng"));
                    builder.setRating(rs.getDouble("rating"));
                    builder.setOpenTime(rs.getDouble("open_time"));
                    builder.setCloseTime(rs.getDouble("close_time"));
                    builder.setSuggestVisitTime(rs.getDouble("suggest_visit_time"));
                    builder.setFormattedAddress(rs.getString("formattedAddress"));
                    builder.setPrice(rs.getDouble("price"));
                    builder.setImageUrl(rs.getString("imageUrl"));
                    builder.setPlaceId(rs.getString("placeId"));
                    builder.setCategories(getCategories(rs.getString("location_id")));

                    allInterests.add(builder.build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allInterests;
    }

    @Override
    public Set<String> getCategories(String itemId) {
        if (conn == null) {
            return new HashSet<>();
        }

        Set<String> categories = new HashSet<>();
        try {
            String sql = "SELECT category FROM categories WHERE location_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }

    @Override
    public Interest getInterestInfo(String locationId) {
        if (conn == null) {
            return null;
        }

        try {
            String sql = "SELECT * FROM interests WHERE location_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, locationId);
            ResultSet rs = stmt.executeQuery();

            InterestBuilder builder = new InterestBuilder();
            while (rs.next()) {
                builder.setLocationId(rs.getString("location_id"));
                builder.setName(rs.getString("name"));
                builder.setLat(rs.getDouble("lat"));
                builder.setLng(rs.getDouble("lng"));
                builder.setRating(rs.getDouble("rating"));
                builder.setOpenTime(rs.getDouble("open_time"));
                builder.setCloseTime(rs.getDouble("close_time"));
                builder.setSuggestVisitTime(rs.getDouble("suggest_visit_time"));
                builder.setFormattedAddress(rs.getString("formattedAddress"));
                builder.setImageUrl(rs.getString("imageUrl"));
                builder.setPrice(rs.getDouble("price"));
                builder.setPlaceId(rs.getString("placeId"));
                builder.setCategories(getCategories(rs.getString("location_id")));
                return builder.build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Interest> searchByName(String name){

        if(conn == null){
            return new ArrayList<>();
        }

        List<Interest> matchingInterests = new ArrayList<>();
        try{
            String sql = "SELECT * FROM interests WHERE interest_id LIKE ?";//if interest name is the interestId in the database
            //String sql = "SELECT * FROM interests WHERE name LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            //put results in our data structure "Interest"
            InterestBuilder builder = new InterestBuilder();
            while(rs.next()){
                builder.setLocationId(rs.getString("location_id"));
                builder.setName(rs.getString("name"));
                builder.setLat(rs.getDouble("lat"));
                builder.setLng(rs.getDouble("lng"));
                builder.setRating(rs.getDouble("rating"));
                builder.setOpenTime(rs.getDouble("open_time"));
                builder.setCloseTime(rs.getDouble("close_time"));
                builder.setSuggestVisitTime(rs.getDouble("suggest_visit_time"));
                builder.setFormattedAddress(rs.getString("formattedAddress"));
                builder.setImageUrl(rs.getString("imageUrl"));
                builder.setPrice(rs.getDouble("price"));
                builder.setPlaceId(rs.getString("placeId"));
                builder.setCategories(getCategories(rs.getString("location_id")));

                matchingInterests.add(builder.build());
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return matchingInterests;
    }

    @Override
    public void saveItem(Interest interest) {

    }

    @Override
    public boolean registration(String username, String password, String firstName, String lastName) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return false;
        }

        try {
            String sql = "INSERT IGNORE INTO users(user_id, password, first_name, last_name) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            int numOfUpdates = ps.executeUpdate();
            return numOfUpdates > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getFullname(String userId) {
        return null;
    }

//    @Override
//    public Set<Item> getFavoriteItems(String userId) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public Set<String> getCategories(String itemId) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public List<Item> searchItems(double lat, double lon, String term) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public void saveItem(Item item) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public String getFullname(String userId) {
//        // TODO Auto-generated method stub
//        return null;
//    }

    @Override
    public boolean verifyLogin(String userId, String password) {
        // TODO Auto-generated method stub
        if (conn == null) {
            System.err.println("DB connection failed");
            return false;
        }
        try{
            PreparedStatement ps = conn.prepareStatement("select user_Id,password from users where user_Id=? and password=?");
            ps.setString(1, userId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Interest> getInterestsByCategory(String category) {
        if (conn == null) {
            return new ArrayList<>();
        }

        List<String> allInterestIds = new ArrayList<>();
        try {
            String sql = "SELECT location_id FROM categories WHERE category = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, category);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allInterestIds.add(rs.getString("location_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Interest> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM interests WHERE location_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            for (String location_id: allInterestIds) {
                statement.setString(1, location_id);
                ResultSet rs = statement.executeQuery();
                InterestBuilder builder = new InterestBuilder();

                while (rs.next()) {
                    builder.setLocationId(rs.getString("location_id"));
                    builder.setName(rs.getString("name"));
                    builder.setLat(rs.getDouble("lat"));
                    builder.setLng(rs.getDouble("lng"));
                    builder.setRating(rs.getDouble("rating"));
                    builder.setOpenTime(rs.getDouble("open_time"));
                    builder.setCloseTime(rs.getDouble("close_time"));
                    builder.setSuggestVisitTime(rs.getDouble("suggest_visit_time"));
                    builder.setFormattedAddress(rs.getString("formattedAddress"));
                    builder.setImageUrl(rs.getString("imageUrl"));
                    builder.setPrice(rs.getDouble("price"));
                    builder.setPlaceId(rs.getString("placeId"));
                    builder.setCategories(getCategories(rs.getString("location_id")));
                    result.add(builder.build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public ArrayList<Interest> getInterestsByLocationId(JSONArray inputJSONArray) {
        if (conn == null) {
            return new ArrayList<>();
        }

        ArrayList<Interest> pinnedInterests = new ArrayList<>();
        try{

            JSONArray pinnedInterestsJSONArray = inputJSONArray;
            System.out.println(pinnedInterestsJSONArray.get(0).toString());

            for(int i = 0; i<pinnedInterestsJSONArray.length(); i++){
                String location_id = inputJSONArray.get(i).toString();
                Interest.InterestBuilder builder = new Interest.InterestBuilder();
                String sql = "SELECT * FROM interests WHERE location_id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, location_id);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    builder.setLocationId(rs.getString("location_id"));
                    builder.setName(rs.getString("name"));
                    builder.setLat(rs.getDouble("lat"));
                    builder.setLng(rs.getDouble("lng"));
                    builder.setRating(rs.getDouble("rating"));
                    builder.setOpenTime(rs.getDouble("open_time"));
                    builder.setCloseTime(rs.getDouble("close_time"));
                    builder.setSuggestVisitTime(rs.getDouble("suggest_visit_time"));
                    builder.setFormattedAddress(rs.getString("formattedAddress"));
                    builder.setPlaceId(rs.getString("placeId"));
                    builder.setCategories(getCategories(rs.getString("location_id")));
                    pinnedInterests.add(builder.build());
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return pinnedInterests;

    }
}
