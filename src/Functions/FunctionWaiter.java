package Functions;

import connection.ConnectionToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import model.Waiter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FunctionWaiter {
    public ObservableList<Waiter> showAllWaiter() throws SQLException {
        ObservableList<Waiter> observableListWaiter = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM user, role, cafe where user.id_adress=cafe.id and user.id_role=role.id_role");

        while(resultSet.next()){

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String adress= resultSet.getString(3);
            String telephone = resultSet.getString(4);
            String login= resultSet.getString(5);
            String password = resultSet.getString(6);
            String role = resultSet.getString(10);
            String adresscafe = resultSet.getString(12);
            String director= resultSet.getString(13);
            observableListWaiter.add(new Waiter(id, name, adress, telephone, login, password, role, adresscafe, director));


        }


        connection.close();
        resultSet.close();
        statement.close();
        return observableListWaiter;
    }

    

    public void addNewWaiter(String nameWaiter, String adress, String telephone,String loginWaiter, String passwordWaiter, int  id_adress,  int id_role) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into user(waiter, adress, telephone, login, password,id_adress, id_role) values('"+nameWaiter+"','"+adress+"','"+telephone+"', '"+loginWaiter+"', '"+passwordWaiter+"','"+id_adress+"','"+id_role+"')");
        connection.close();
        statement.close();
    }

    public int checkId(String name,  String adress, String telephone, String login, String password) throws SQLException {
        int id = 0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT id FROM user where waiter='"+name+"'and adress='"+adress+"' and telephone='"+telephone+"' and login='"+login+"' and password='"+password+"'");
        while(resultSet.next()){

            id = resultSet.getInt(1);


        }
        statement.close();
        resultSet.close();
        connection.close();
        return id;
    }

    public void showAlertEmptyFiel() {
    }

    public void updateDataWaiter(int id_waiter, String name, String adress, String telephone, String login, String password, int idRole) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update user set waiter='"+name+"', adress='"+adress+"', telephone='"+telephone+"', login='"+login+"', password='"+password+"', id_role='"+idRole+"'  where id='"+id_waiter+"'");
        statement.close();
        connection.close();
    }

    public void deleteWaiter(int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from user where id='"+id+"'");
        statement.close();
        connection.close();
    }

    public ObservableList<String> getRoles() throws SQLException {
        ObservableList<String> roles = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM role");

        while(resultSet.next()){
                roles.add(resultSet.getString(2));
        }


        connection.close();
        resultSet.close();
        statement.close();
        return  roles;
    }

    public int getIdRole(String roleValue) throws SQLException {

        int  id =0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT id_role FROM role where  namerole='"+roleValue+"'");

        while(resultSet.next()){
           id = resultSet.getInt(1);
        }


        connection.close();
        resultSet.close();
        statement.close();
        return  id;
    }

    public ObservableList <String> getAdressCafe() throws SQLException {
        ObservableList<String> adressList = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM cafe");

        while(resultSet.next()){
            adressList.add(resultSet.getString(2));
        }


        connection.close();
        resultSet.close();
        statement.close();
        return  adressList;
    }

    public int getAdressCafeID(String adresscafe) throws SQLException {
        int  id = 0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT id FROM cafe where adress='"+adresscafe+"'");

        while(resultSet.next()){
          id = resultSet.getInt(1);
        }


        connection.close();
        resultSet.close();
        statement.close();
        return id;
    }

    public int getRolesId(String rolecafe) throws SQLException {
        int id=0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT id_role  FROM role where namerole='"+rolecafe+"'");

        while(resultSet.next()){
           id = resultSet.getInt(1);
        }


        connection.close();
        resultSet.close();
        statement.close();
        return id;
    }
}
