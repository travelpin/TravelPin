package db.mysql;

import java.sql.*;
import java.util.*;

import db.DBConnection;
import entity.Interest;
import entity.Interest.InterestBuilder;
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
    public void setFavoriteInterests(String user_id, List<String> liked_locations) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "INSERT IGNORE INTO users(user_id, liked_location) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user_id);
            for (String liked_location : liked_locations) {
                ps.setString(2, liked_location);
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void unsetFavoriteInterests(String user_id, List<String> liked_locations) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "DELETE FROM users WHERE user_id = ? AND liked_location = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user_id);
            for (String liked_location : liked_locations) {
                ps.setString(2, liked_location);
                ps.execute();
            }

        } catch (Exception e) {
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
    public Set<String> getFavoriteInterestIds(String user_id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Interest> getFavoriteInterests(String userId) {
        return null;
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
    public Interest getInterestInfo(String interestId) {
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
            String sql = "INSERT IGNORE INTO users VALUES (?,?,?,?)";
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
        return false;
    }

}
