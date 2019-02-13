package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MySQLConnection implements DBConnection {
    private Connection connection;
    public MySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            connection = DriverManager.getConnection(MySQLDBUtil.URL);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void setFavoriteItems(String userId, List<String> interestIds) {
        if (connection == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "INSERT IGNORE INTO history(user_id, interest_id) VALUE (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userId);
            for (String interestId : interestIds) {
                ps.setString(2, interestId);
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unsetFavoriteItems(String userId, List<String> interestIds) {
        if(connection == null) {
            System.err.println("DB connection failed");
            return;
        }

        try {
            String sql = "DELETE FROM history WHERE user_id = ? AND interest_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userId);
            for (String interestId : interestIds) {
                ps.setString(2, interestId);
                ps.execute();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


    }
}






















