package demo.keploy.db;

import io.keploy.ksql.KDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DbUtil {
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_USER = "mysql";
    private static final String DB_PASS = "mysql";
    private static Connection CONNECTION = null;

    private static Connection getConnection() {

        if (CONNECTION != null)
            return CONNECTION;
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            final Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                final Driver driver = drivers.nextElement();
                System.out.println("Driver in DBUtil: " + driver);
            }

            KDriver.WrapDriver();

            connection =
                    DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/urlshortener?" +
                            "user=" + DB_USER + "&password=" + DB_PASS);


        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        CONNECTION = connection;
        return CONNECTION;
    }

    public static void addShortenUrl(String url, String shortUrl) throws Exception {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `urlshortener`.`urls` (`url`, `shortenurl`) VALUES (?, ?)");
            preparedStatement.setString(1, url);
            preparedStatement.setString(2, shortUrl);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new Exception(throwables.getMessage());
        }

    }

    public static String getUrl(String shortUrl) throws Exception {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT url FROM `urlshortener`.`urls` where shortenurl=?");

            preparedStatement.setString(1, shortUrl);
            ResultSet resultSet = preparedStatement.executeQuery();
            String str = "";
            while (resultSet.next()) {
                str = resultSet.getString(1);
                System.out.println("printing in dbutil" + str);
            }
            return str;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new Exception(throwables.getMessage());
        }
    }
}
