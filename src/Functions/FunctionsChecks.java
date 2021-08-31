package Functions;

import connection.ConnectionToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CheckOrderModel;
import model.Checks;
import model.Waiter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FunctionsChecks {
    public ObservableList<Checks> getDataChecks() throws SQLException {
        ObservableList<Checks> checks = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT idscope, datescope,time,pricescope FROM scope");

        while(resultSet.next()){
                checks.add(new Checks(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4)));
        }


        connection.close();
        resultSet.close();
        statement.close();
        return checks;
    }

    public boolean getSearchData(String data) {
        try
        {
            int d =Integer.parseInt(data);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public ObservableList<Checks> getDataDate(String text) throws SQLException {
        ObservableList<Checks> checkDate = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT idscope, datescope,time,pricescope FROM scope where datescope='"+text+"'");

        while(resultSet.next()){
            checkDate.add(new Checks(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4)));
        }


        connection.close();
        resultSet.close();
        statement.close();
       return checkDate;
    }

    public ObservableList<Checks> getDataCheckId(int parseInt) throws SQLException {
        ObservableList<Checks> checkId = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT idscope, datescope,time,pricescope FROM scope where idscope='"+parseInt+"'");

        while(resultSet.next()){
            checkId.add(new Checks(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4)));
        }


        connection.close();
        resultSet.close();
        statement.close();
        return checkId;
    }

    public ObservableList<CheckOrderModel> getdata(int id) throws SQLException {
        ObservableList<CheckOrderModel> checks = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select  scope.idscope,actionorders.count,name_dish,dish.newprice, dish.newprice*actionorders.count from  role,user, dish,scope,actionorders where scope.idscope='"+id+"' and scope.idorder = actionorders.id and actionorders.id_waiter=user.id and user.id_role=role.id_role and dish.id = actionorders.id_dish;");

        while(resultSet.next()){
            checks.add(new CheckOrderModel(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4),resultSet.getFloat(5)));
        }


        connection.close();
        resultSet.close();
        statement.close();
        return checks;
    }
}
