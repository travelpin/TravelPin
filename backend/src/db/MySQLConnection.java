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
    public void likeInterests(String userId, List<String> interestIds) {
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
    public void dislikeInterests(String userId, List<String> interestIds) {
        if (connection == null) {
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

    @Override
    public Interest getInterestInfo(String interestId) {
        if (connection == null) {
            return null;
        }

        try {
            String sql = "SELECT * FROM interests WHERE interest_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, interestId);
            ResultSet rs = stmt.executeQuery();

            InterestBuilder builder = new InterestBuilder();
            while(rs.next) {
                builder.setInterestId(rs.getString("interest_id"));
                builder.setName(rs.getString("name"));
                builder.setAddress(rs.getString("address"));
                builder.setImageUrl(rs.getString("image_url"));
                builder.setOpenTime(rs.getInt("open_time"));
                builder.setCloseTime(rs.getInt("close_time"));
                builder.setRanking(rs.getInt("ranking"));
                builder.setRating(rs.getDouble("rating"));
                builder.setTicketPrice(rs.getDouble("ticket_price"));
                builder.setSuggestedVisitTime(rs.getInt("suggested_visit_time"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    @Override
    public Set<Interest> getFavoriteInterests(String userId) {
        if (connection == null) {
            return new HashSet<>();
        }

        Set<Interest> favoriteInterests = new HashSet<>();
        Set<String> interestIds = getFavoriteInterestIds(userId);

        try {
            String sql = "SELECT * FROM interests WHERE interest_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (String interestId : interestIds) {
                stmt.setString(1, interestId);

                ResultSet rs = stmt.executeQuery();

                InterestBuilder builder = new InterestBuilder();

                while(rs.next()) {
                    builder.setInterestId(rs.getString("interest_id"));
                    builder.setName(rs.getString("name"));
                    builder.setAddress(rs.getString("address"));
                    builder.setImageUrl(rs.getString("image_url"));
                    builder.setOpenTime(rs.getInt("open_time"));
                    builder.setCloseTime(rs.getInt("close_time"));
                    builder.setRanking(rs.getInt("ranking"));
                    builder.setRating(rs.getDouble("rating"));
                    builder.setTicketPrice(rs.getDouble("ticket_price"));
                    builder.setSuggestedVisitTime(rs.getInt("suggested_visit_time"));

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
        if (connection == null) {
            return new HashSet<>();
        }

        Set<String> favoriteInterestIds = new HashSet<>();

        try {
            String sql = "SELECT interest_id FROM history WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String interestId = rs.getString("interest_id");
                favoriteInterestIds.add(interestId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoriteInterestIds;
    }
    
    @Override 
    public List<Interest> searchByName(String name){
        if(connection == null){
            return new ArrayList<>();
        }

        List<Interest> matchingInterests = new ArrayList<>();
        try{
            String sql = "SELECT * FROM interests WHERE interest_id LIKE ?";//if interest name is the interestId in the database
            //String sql = "SELECT * FROM interests WHERE name LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            //put results in our data structure "Interest"
            InterestBuilder builder = new InterestBuilder();
            while(rs.next()){
            	builder.setInterestId(rs.getString("interest_id"));
                builder.setName(rs.getString("name"));
                builder.setAddress(rs.getString("address"));
                builder.setImageUrl(rs.getString("image_url"));
                builder.setOpenTime(rs.getInt("open_time"));
                builder.setCloseTime(rs.getInt("close_time"));
                builder.setRanking(rs.getInt("ranking"));
                builder.setRating(rs.getDouble("rating"));
                builder.setTicketPrice(rs.getDouble("ticket_price"));
                builder.setSuggestedVisitTime(rs.getInt("suggested_visit_time"));

                matchingInterests.add(builder.build());
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return matchingInterests;
    }
}






















