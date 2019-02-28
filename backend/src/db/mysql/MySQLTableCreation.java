
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
            String sql = "DROP TABLE IF EXISTS history";
            statement.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS categories";
            statement.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS interests";
            statement.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(sql);

            // Step 3 Create new tables
            sql = "CREATE TABLE interests ("
                    + "location_id VARCHAR(255) NOT NULL,"
                    + "name VARCHAR(255),"
                    + "lat FLOAT,"
                    + "lng FLOAT,"
                    + "rating DOUBLE,"
                    + "open_time FLOAT,"
                    + "close_time FLOAT,"
                    + "suggest_visit_time DOUBLE,"
                    + "formattedAddress VARCHAR(255),"
                    + "imageUrl VARCHAR(255),"
                    + "price FLOAT,"
                    + "placeId VARCHAR(255),"
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

            sql = "CREATE TABLE categories ("
                    + "location_id VARCHAR(255) NOT NULL,"
                    + "category VARCHAR(255) NOT NULL,"
                    + "PRIMARY KEY (location_id, category),"
                    + "FOREIGN KEY (location_id) REFERENCES interests(location_id)"
                    + ")";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE history ("
                    + "user_id VARCHAR(255) NOT NULL,"
                    + "location_id VARCHAR(255) NOT NULL,"
                    + "last_favor_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (user_id, location_id),"
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id),"
                    + "FOREIGN KEY (location_id) REFERENCES interests(location_id)"
                    + ")";
            statement.executeUpdate(sql);

            // Step 4: insert fake user 1111/3229c1097c00d497a0fd282d586be050
            sql = "INSERT INTO users VALUES('1111', '3229c1097c00d497a0fd282d586be050', 'Jim', 'Gray', 'mock_liked_location', 'mock_liked_plan')";
            statement.executeUpdate(sql);


            sql = "INSERT INTO interests(`location_id`, `name`, `lat`, `lng`, `rating`, `open_time`, `close_time`, `suggest_visit_time`, `formattedAddress`, `price`, `imageUrl`, `placeId`) VALUES " +
                    "('0f223133b007b4518e8fb6f6088cbb0046eeabe4', 'La Guardia & Wagner Archives', 40.744, -73.935, 3.3, 0, 0, 1, '3110 Thomson Avenue, Long Island City', 0, 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Fiorella_LaGuardia_statue.jpg/220px-Fiorella_LaGuardia_statue.jpg', 'ChIJS3gYNCtZwokRSy8JvCLT33I')," +
                    "('1501437dc4cf50d24e9ada4f59ae02057f0de034', 'Museum of Modern Art', 40.7438, -73.9324, 4.6, 0, 0, 3, '45-20 33rd Street, Long Island City', 25, 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/MoMa_NY_USA_1.jpg/220px-MoMa_NY_USA_1.jpg', 'ChIJKxDbe_lYwokRIZ0DdvDjKwY')," +
            "('16c11408a9534f958d1f6aaeeb9d97f82bd4057a', 'new new york', 40.7427, -73.9313, 4.5, 0, 0, 1, '46-0-46-98 34th Street, Long Island City',0, 'https://media-cdn.tripadvisor.com/media/photo-s/14/d4/78/c7/exterior.jpg', 'ChIJk0u19dNewokRRhHTBpivM3E')," +
            "('2aa2cd787b451cd1605db8c59cdc89b74954a90c', 'Swaai Boys International Headquarters', 40.7332, -73.9573, 4.5, 0, 0, 1, '104 Green Street, Brooklyn',0, 'http://4.bp.blogspot.com/-vsQIUh02I6k/UrM4BVhLxjI/AAAAAAAAH2Y/3eihIbDHUIM/s1600/Hollers+Trailer+Promo+HQ.jpg', 'ChIJYU9JzJ5ZwokRQDdQddbI5Vw')," +
            "('6c25e19f62b8abe77e9cde979ea0f841a71d7ba6', 'Self-Taught Genius Gallery', 40.7418, -73.9331, 4.5, 0, 0, 2, '47-29 32nd Place, Long Island City', 20, 'https://d1smv7h0armdzg.cloudfront.net/wp-content/uploads/2017/10/Self_taugh_genius.jpg', 'ChIJJc8_edNewokRbVJ3TFCCGtA')," +
            "('7596e251ea16aa50e4680699100d96af231c2053', 'Museum of Food and Drink (MOFAD) Lab', 40.7188, -73.9496, 4.2, 0, 0, 2, '62 Bayard Street, Brooklyn', 14, 'http://cdn.brownstoner.com/wp-content/uploads/2015/10/museum-of-food-and-drink-brooklyn-williamsburg-photos-03.jpg', 'ChIJ1-v_8VpZwokRwobN_5U_vVE')," +
            "('885be5960597801e043f42ab0227719e7b075f47', 'MoMA PS1', 40.7455, -73.9474, 4.4, 0, 0, 2, '22-25 Jackson Avenue, Long Island City', 25, 'https://www.moma.org/d/p/sa/MoMAPS1.jpg', 'ChIJwfbFiiNZwokRN8hnF940DbY')," +
            "('9bab5783e8cfef840e9267008252e70220ab0210', 'IFAC-Arts', 40.7262, -73.9525, 4.5, 0, 0, 2, '735 Manhattan Avenue, Brooklyn', 0, 'http://s3.amazonaws.com/les.nyc/wp-content/uploads/2018/07/ifac-athina-situation-front-left-rear1-800x599.jpg', 'ChIJE0sIoUZZwokROhsq9kZKRwo')," +
            "('a4cdbf9b01276a4da5ec35cac9b4a4940abdb99d', 'Instytut Pilsudskiego', 40.7299, -73.955, 5, 0, 0, 1, '138 Greenpoint Avenue, Brooklyn', 25, 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Hotel_Washington_NYC.jpg/220px-Hotel_Washington_NYC.jpg', 'ChIJYRZIwEBZwokROnTnnqeT2Cg')," +
            "('ba73865214ceb74cee7e59de57330a0be9355471', 'SculptureCenter', 40.7469, -73.9408, 4.4, 0, 0, 2, '44-19 Purves Street, Long Island City', 10, 'http://www.nashersculpturecenter.org/images/NSC_Carousel/nasher-sculpture-center-gallery-one.jpg', 'ChIJg8J0MipZwokRO03w0rQvXJc')," +
            "('c0749de1de94b9d613eca88a2e2f763062413bfc', 'Johnston Mausoleum', 40.7333, -73.928, 4.8, 0, 0, 1, 'Maspeth', 0, 'https://c1.staticflickr.com/3/2383/2103906736_9e4a5969c2_b.jpg', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('e084e617e650662f60da9267dd12dbeacc368669ed084fd4e452f89f7ba05ed3', 'Statue of Liberty', 40.6892, -74.0445, 4.6, 0, 0, 4, 'New York, NY 10004', 18.5, 'https://images.unsplash.com/photo-1542811951-6d0192f19875?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=633&q=80', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('da485a215f862664c64ededaf61616bb3d5cc02004c094f2e6a6966b44cc43a7', 'Central Park', 40.7829, -73.9654, 4.8, 0, 0, 2, 'New York, NY', 0, 'https://images.unsplash.com/photo-1542622805-980533ee81ae?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('18bc00fce7a80224f343529310dfc37a2c67fd631f640498f74cdfe8dfcbb2c4', 'Rockefeller Center', 40.7587, -73.9787, 4.6, 0, 0, 2, '45 Rockefeller Plaza, New York, NY 10111', 27.22, 'https://images.unsplash.com/photo-1546333069-b263afca9d69?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('f3124907f7cd8ad1c43d9567889e37917bd62db7f22e73f17652232a69d3858a', 'Metropolitan Museum of Art', 40.7794, -73.9632, 4.8, 0, 0, 4, '1000 5th Ave, New York, NY 10028', 25, 'https://media-cdn.tripadvisor.com/media/photo-s/0d/f4/f9/de/the-metropolitan-museum.jpg', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('fafe7b5693cb8a9fc666d1dfa5ecf96f26fa5527d94bb02804a9fddaa06abcb6', 'Broadway and the Theater District', 40.7590, -73.9845, 4.5, 0, 0, 2, '10018, 10019, 10036 NYC', 0, 'https://cdn.vox-cdn.com/thumbor/CSoyhWr_5WQy6MLCmVcc_TMD0L0=/0x0:3702x2371/1200x900/filters:focal(1555x890:2147x1482)/cdn.vox-cdn.com/uploads/chorus_image/image/56014555/shutterstock_69846691.0.jpg', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('97f33193a3931c37a8642c8680788842bcf0247aee030e42a4aed19c34cd83fe', 'Empire State Building', 40.7484, -73.9857, 4.6, 0, 0, 2, '20 W 34th St, New York, NY 10001', 20, 'https://images.unsplash.com/photo-1543716091-a840c05249ec?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('28a691d41f4326fe0c3e36c7b1e558b98376f0d512e9f94f05d30e1173e4ee66', '9/11 Memorial and Museum', 40.7115, -74.0134, 4.8, 0, 0, 2, '180 Greenwich St, New York, NY 10007', 24, 'https://images.unsplash.com/photo-1501456045421-4ff952f0692c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('4c90a71790d26da780193c327c3728965a9c59085c4097e2e56c09afc4ba0f4d', 'High Line', 40.7480, -74.0048, 4.7, 0, 0, 1, 'New York, NY 10011', 0, 'https://cdn-images-1.medium.com/max/1200/1*cCyFit6fT6-2gb5hqIYKAg.jpeg', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('0725595dbe4ec359f7f07f5480ba9d7528f21ecffc614e715b009c285bb543be', 'Time Square', 40.7590, -73.9845, 4.7, 0, 0, 3, 'Manhattan, NY 10036', 0, 'https://images.unsplash.com/photo-1529374685586-e9813d255de1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('3297615f561a06055612a4654a943ff3645e884af20ffd0ab380d583ead63157', 'Brooklyn Bridge', 40.7061, -73.9969, 4.8, 0, 0, 1, 'Brooklyn Bridge, New York, NY 10038', 0, 'https://images.unsplash.com/photo-1540264565589-f9bbde7d8892?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1101&q=80', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')," +
            "('f202f6771e9e68cf296e01f3f53a376706c4d4a6ddca6f48bbb195ca36f7e16f', 'New York Public Library', 40.7532, -73.9823, 4.7, 0, 0, 1, '476 5th Ave, New York, NY 10018', 0, 'https://images.unsplash.com/photo-1455732110486-e561f9129b72?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1189&q=80', 'ChIJuQCWxM5ewokRyFBe_kLtgJo')";

            statement.executeUpdate(sql);


            conn.close();
            System.out.println("Import done successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

