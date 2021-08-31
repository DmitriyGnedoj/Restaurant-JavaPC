package Functions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Dish;

import connection.ConnectionToDB;
import model.SkladDIshIngradient;
import model.TypeDish;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class FunctionDish {
    public ObservableList<Dish> viewsAllDish() throws SQLException {

        ObservableList<Dish> dishObservableList = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM dish,groupdish where dish.id_groupdish=groupdish.id_groupdish");
        while(resultSet.next()){

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            float price = resultSet.getFloat(3);
            float pricenacenka = resultSet.getInt(5);
            String type = resultSet.getString(7);
            float procent = resultSet.getInt(8);

            dishObservableList.add(new Dish(id, name, price,  type, pricenacenka, procent));
            System.out.println(name);

        }

        connection.close();
        resultSet.close();

        statement.close();
        return  dishObservableList;
    }

    public void addNewDish(String nameDish, float price, int id, float newPrice) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO dish(name_dish, price, id_groupdish, newprice)"+" VALUEs('" + nameDish + "','"+price+"','"+id+"','"+newPrice+"')");
        connection.close();


        statement.close();
    }

    public int checkDishInDatabase(String nameDish) throws SQLException {
        int id = 0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT id FROM dish where name_dish='"+nameDish+"'");
        while(resultSet.next()){

            id = resultSet.getInt(1);


        }

        connection.close();
        resultSet.close();

        statement.close();
        return id;
    }

    public void updateNameDish(int id, String newName, int idType) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update dish set name_dish='"+newName+"', id_groupdish='"+idType+"' where id='"+id+"'" );
        connection.close();

        statement.close();
    }

    public void deleteItem(int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from  dish where id='"+id+"'" );
        connection.close();

        statement.close();
    }

    public ObservableList<Dish> functionSearchDish(String text) throws SQLException {
        ObservableList<Dish> dishList = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from dish where name_dish LIKE '"+text+"%'");
        while(resultSet.next()){

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            float price = resultSet.getFloat(3);
            String type = resultSet.getString(4);
            float pricenacenka = resultSet.getInt(5);
            float procent = resultSet.getInt(6);
            dishList.add(new Dish(id, name,  price,  type, pricenacenka, procent));
            System.out.println(name);

        }

        connection.close();
        resultSet.close();

        statement.close();
        return dishList;
    }

    public ObservableList<String> getTypesDish() throws SQLException {
        ObservableList<String> type = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM groupdish");
        while(resultSet.next()){

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            type.add(name);
            System.out.println(name);

        }

        connection.close();
        resultSet.close();

        statement.close();
        return  type;
    }

    public int getIdType(String typeDish) throws SQLException {
       int id=0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT groupdish.id_groupdish FROM  groupdish where namegroupdish='"+typeDish+"'");
        while(resultSet.next()){

          id = resultSet.getInt(1);


        }

        connection.close();
        resultSet.close();

        statement.close();
        return  id;
    }

    public float GetProcent(float a) {
      return   1+(a/100);
    }
}
