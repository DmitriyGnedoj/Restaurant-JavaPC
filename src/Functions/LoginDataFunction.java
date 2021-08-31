package Functions;

import connection.ConnectionToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Dish;
import model.LoginForm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDataFunction {
    public ObservableList<LoginForm> getDataUsers() throws SQLException {
        ObservableList<LoginForm> list = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT login, password, namerole FROM user, role where user.id_role=role.id_role");
        while(resultSet.next()){
            list.add(new LoginForm(resultSet.getString(1),  resultSet.getString(2), resultSet.getString(3)));


        }

        connection.close();
        resultSet.close();

        statement.close();
        return  list;
    }

    public String checkLoginData(String login, String password) throws SQLException {
        String role=null;
        int counter=0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT namerole FROM user, role where user.login='"+login+"' and user.password='"+password+"' and user.id_role=role.id_role");
        while(resultSet.next()){
            role = resultSet.getString(1);
            counter++;
            }

        connection.close();
        resultSet.close();

        statement.close();
        if(counter==0){
            role="not";
        }
        System.out.println(role);
              return  role;
    }
}
