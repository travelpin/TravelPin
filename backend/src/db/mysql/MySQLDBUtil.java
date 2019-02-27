package db.mysql;

/*
Author: Debbie Liang
Date: Feb. 2019
 */


public class MySQLDBUtil {
    private static final String HOSTNAME = "localhost";
    private static final String PORT_NUM = "3307"; // change it to your mysql port number
    public static final String DB_NAME = "TravelPin";
    private static final String USERNAME = "root"; //depend on your own config
    private static final String PASSWORD = "root"; //depend on your own config
    public static final String URL = "jdbc:mysql://"
            + HOSTNAME + ":" + PORT_NUM + "/" + DB_NAME
            + "?user=" + USERNAME + "&password=" + PASSWORD
            + "&autoReconnect=true&serverTimezone=UTC";   // this URL is subject to change
}
