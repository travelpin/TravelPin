package db.mysql;

import java.util.List;
import java.util.Set;

import db.DBConnection;
import entity.Interest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    @Override
    public void setFavoritePlans(String user_id, List<String> liked_plans) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "INSERT IGNORE INTO users(user_id, liked_plan) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user_id);
            for (String liked_plan : liked_plans) {
                ps.setString(2, liked_plan);
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsetFavoritePlans(String user_id, List<String> liked_plans) {
        if (conn == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "DELETE FROM users WHERE user_id = ? AND liked_plan = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user_id);
            for (String liked_plan : liked_plans) {
                ps.setString(2, liked_plan);
                ps.execute();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void likeInterests(String userId, List<String> interestIds) {

    }

    @Override
    public void dislikeInterests(String userId, List<String> interestIds) {

    }

    @Override
    public Interest getInterestInfo(String interestId) {
        return null;
    }

    @Override
    public Set<Interest> getFavoriteInterests(String userId) {
        return null;
    }

    @Override
    public Set<String> getFavoriteInterestIds(String user_id) {
        // TODO Auto-generated method stub
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
