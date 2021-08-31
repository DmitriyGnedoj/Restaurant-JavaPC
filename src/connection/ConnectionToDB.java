package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {
    //String url = "jdbc:mysql://mysql.zzz.com.ua:3306/dimasik2021";
    String url = "jdbc:mysql://localhost:3306/mydb";
   // String url = "ru.000webhost.com/:3306/id16435024_test";
    String user = "dima";
    String password = "sD>8oxn6mtc0!0Fq";

    Connection connection;



    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

}
