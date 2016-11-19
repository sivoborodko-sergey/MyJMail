package jmail.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Глеб on 14.11.2014.
 */
public class DBConnectionFactory {

    private final String HOST;
    private final String PORT;
    private final String USER;
    private final String PASS;
    private static DBConnectionFactory INSTANCE = null;

    public DBConnectionFactory(String host, String port, String user, String pass) {
        this.HOST = host;
        this.PORT = port;
        this.USER = user;
        this.PASS = pass;
    }

    public static Connection getConnection() throws SQLException {
        if(INSTANCE == null) {
            Properties properties = new Properties();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                properties.load(new FileInputStream("src/main/resources/db.properties"));
                String host = properties.getProperty("jdbc.host");
                String port = properties.getProperty("jdbc.port");
                String user = properties.getProperty("jdbc.user");
                String pass = properties.getProperty("jdbc.pass");
                INSTANCE = new DBConnectionFactory(host, port, user, pass);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", INSTANCE.HOST, INSTANCE.PORT, "jmail"),
                INSTANCE.USER, INSTANCE.PASS);
    }

}
