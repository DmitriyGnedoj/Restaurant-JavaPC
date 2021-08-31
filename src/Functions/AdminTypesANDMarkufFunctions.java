package Functions;

import connection.ConnectionToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Dish;
import model.Markup;
import model.Type;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminTypesANDMarkufFunctions {
    public ObservableList<Type> getTypes() throws SQLException {
        ObservableList<Type> types = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM foodunits");
        while(resultSet.next()){

            int id = resultSet.getInt(1);
            String unit = resultSet.getString(2);
            types.add(new Type(id, unit));


        }

        connection.close();
        resultSet.close();

        statement.close();
        return  types;
    }

    public ObservableList<Markup> getMarkups() throws SQLException {
        ObservableList<Markup> markups = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM groupdish");
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String type = resultSet.getString(2);
            float markup = resultSet.getFloat(3);
            markups.add(new Markup(id, type, markup));

        }

        connection.close();
        resultSet.close();

        statement.close();
        return  markups;
    }

    public void createNewMarkup(String type, float markup) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO groupdish(namegroupdish, markup)"+" VALUEs('"+type+"',"+markup+")");
        connection.close();


        statement.close();
    }

    public void createNewFoodUnit(String type) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO foodunits(unit)"+" VALUE('"+type+"')");
        connection.close();


        statement.close();
    }

    public void editMarkup(int id, String name, float markupValue) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update groupdish set namegroupdish='"+name+"', markup='"+markupValue+"' where id_groupdish='"+id+"'" );
        connection.close();

        statement.close();
    }

    public void editType(int id, String name) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update foodunits set unit='"+name+"' where id_unit='"+id+"'" );
        connection.close();

        statement.close();
    }

    public void deleteValueMarkup(int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from  groupdish where id_groupdish='"+id+"'" );
        connection.close();

        statement.close();
    }

    public void deleteValueFoodUnit(int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from  foodunits where id_unit='"+id+"'" );
        connection.close();

        statement.close();
    }
}
