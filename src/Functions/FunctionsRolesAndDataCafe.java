package Functions;

import connection.ConnectionToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.CafeData;
import model.Dish;
import model.Roles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FunctionsRolesAndDataCafe {
    public ObservableList<Roles> getRoles() throws SQLException {
        ObservableList<Roles> roles = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM role");
        while(resultSet.next()){
            roles.add(new Roles(resultSet.getInt(1), resultSet.getString(2)));
        }

        connection.close();
        resultSet.close();

        statement.close();
        return  roles;
    }

    public ObservableList<CafeData> getDataCafe() throws SQLException {
        ObservableList<CafeData> cafeData = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM cafe");
        while(resultSet.next()){
            cafeData.add(new CafeData(resultSet.getInt(1), resultSet.getString(2),  resultSet.getString(3)));
        }

        connection.close();
        resultSet.close();

        statement.close();
        return cafeData;
    }

    public void addRole(String fieldOne) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO role(namerole)"+" VALUE('"+fieldOne+"')");
        connection.close();


        statement.close();
    }

    public void addAdress(String fieldOne, String fieldTwo) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO cafe(adress,  director)"+" VALUE('"+fieldOne+"', '"+fieldTwo+"')");
        connection.close();


        statement.close();
    }

    public void deleteRole(int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from  role where id_role='"+id+"'" );
        connection.close();

        statement.close();
    }

    public void deleteCafeData(int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from  cafe where id='"+id+"'" );
        connection.close();

        statement.close();
    }

    public void editDataAdress(String fieldOne, int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update role set namerole='"+fieldOne+"' where id_role='"+id+"'");
        connection.close();
        statement.close();
    }

    public void editDataCafe(String fieldOne, String fieldTwo, int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update cafe set adress='"+fieldOne+"', director='"+fieldTwo+"' where id='"+id+"'");
        connection.close();
        statement.close();
    }
}
