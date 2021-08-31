package Functions;

import connection.ConnectionToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ingradients;
import model.OrderAction;
import model.UnitDish;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FunctionIngradient {


    public ObservableList<Ingradients> showAllIngradients() throws SQLException {
        ObservableList<Ingradients> listIngradient = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select ` ingredient`.`id_ ingredient`,` ingredient`.`name_ ingredient`, ` ingredient`.count, ` ingredient`.price, foodunits.unit from ` ingredient`, foodunits where ` ingredient`.id_unit=foodunits.id_unit");

        while(resultSet.next()){
            listIngradient.add(new Ingradients(resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(3), resultSet.getFloat(4),resultSet.getString(5)));
        }



        connection.close();
        resultSet.close();
        statement.close();
        return listIngradient;
    }

    public String getNamesUnitDsidh() throws SQLException {
        String nameUnit = "";
        int firstId=0;
        //SELECT foodunits.id_unit from foodunits  ORDER BY foodunits.id_unit ASC LIMIT 1
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT foodunits.unit from foodunits  ORDER BY foodunits.id_unit ASC LIMIT 1");
        while(resultSet.next()){
            nameUnit = resultSet.getString(1);
        }
        System.out.println(nameUnit);
        connection.close();
        statement.close();
        resultSet.close();
        return  nameUnit;
    }

    public ObservableList<String> getFoodUnits() throws SQLException {
        ObservableList<String> listUnit =  FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from foodunits");

        while(resultSet.next()){
           // listIngradient.add(new Ingradients(resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(3), resultSet.getString(4)));
           listUnit.add(resultSet.getString(2));

        }



        connection.close();
        resultSet.close();
        statement.close();
        return listUnit;
    }

    public int getIdUnit(String unit) throws SQLException {
        int id = 0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id_unit from foodunits where unit='"+unit+"'");
        while(resultSet.next()) {
            id = resultSet.getInt(1);
        }
        connection.close();
        statement.close();
        resultSet.close();
        return id;
    }

    public void createNewIngradient(String name, float count, float price, int id_unit) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into ` ingredient`(`name_ ingredient`, id_unit, count, price) values ('"+name+"','"+id_unit+"','"+count+"','"+price+"')");
        connection.close();

    }

    public void dataIngradient(int id_edt, String name, float count, float price, int id_unit) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update ` ingredient` set `name_ ingredient`='"+name+"', id_unit='"+id_unit+"', count ='"+count+"', price='"+price+"' where `id_ ingredient`='"+id_edt+"'" );
        connection.close();
        statement.close();
    }

    public void deleteIngradient(int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from  ` ingredient` where `id_ ingredient`='"+id+"'" );
        connection.close();

        statement.close();
    }

    public ObservableList<Ingradients> searchData(String search) throws SQLException {
        ObservableList<Ingradients> listIngradient = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from ` ingredient` where ` ingredient`.`name_ ingredient` LIKE '"+search+"%'");

        while(resultSet.next()){
            listIngradient.add(new Ingradients(resultSet.getInt(1), resultSet.getString(2), resultSet.getFloat(3), resultSet.getFloat(4),resultSet.getString(5)));
        }



        connection.close();
        resultSet.close();
        statement.close();
        return listIngradient;
    }
}
