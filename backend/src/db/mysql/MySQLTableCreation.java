
package db.mysql;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

/*
Author: Debbie Liang
Date: Feb. 2019
 */

public class MySQLTableCreation {
    // Run this as Java application to reset db schema.
    public static void main(String[] args) {
        try {
            // Step 1 Connect to MySQL.
            System.out.println("Connecting to " + MySQLDBUtil.URL);
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);

            if (conn == null) {
                return;
            }

            // Step 2 Drop tables in case they exist.
            Statement statement = conn.createStatement();
            String sql = "DROP TABLE IF EXISTS interests";
            statement.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(sql);


            // Step 3 Create new tables
            sql = "CREATE TABLE interests ("
                    + "name VARCHAR(255) NOT NULL,"
                    + "location_id VARCHAR(255),"
                    + "coordinate_x FLOAT,"
                    + "coordinate_y FLOAT,"
                    + "category VARCHAR(255),"
                    + "url VARCHAR(255),"
                    + "suggested_time FLOAT,"
                    + "open_time INT," // the default is this place is gonna be open in the morning at this time
                    + "close_time FLOAT," // the default is this place is gonna be closed in the afternoon at this time
                    + "price FLOAT," // the default is the full price
                    + "rating INT," // optional
                    + "PRIMARY KEY (location_id)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE users ("
                    + "user_id VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "first_name VARCHAR(255),"
                    + "last_name VARCHAR(255),"
                    + "liked_location VARCHAR(255),"
                    // SQL cannot store lists.
                    // So please serialize the two fields to strings below before saving to the database
                    + "liked_plan VARCHAR(255),"
                    + "PRIMARY KEY (user_id)"
                    + ")";
            statement.executeUpdate(sql);


            // Step 4: insert fake user 1111/3229c1097c00d497a0fd282d586be050
            sql = "INSERT INTO users VALUES('1111', '3229c1097c00d497a0fd282d586be050', 'Jim', 'Gray', 'mock_liked_location', 'mock_liked_plan')";
            statement.executeUpdate(sql);


            conn.close();
            System.out.println("Import done successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

