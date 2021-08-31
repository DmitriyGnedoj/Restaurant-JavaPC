package Functions;

import connection.ConnectionToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Dish;
import model.IngradientDish;
import model.SkladDIshIngradient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FunctionsControllerDishIngradients {
    public ObservableList<SkladDIshIngradient> showDataSkladIngradints() throws SQLException {
        ObservableList<SkladDIshIngradient> ingradientDishes = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select `id_ ingredient`, `name_ ingredient`, price from  ` ingredient`");
        while(resultSet.next()){

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            float price = resultSet.getFloat(3);
            ingradientDishes.add(new SkladDIshIngradient(id,name, price) );
            System.out.println(name);

        }

        connection.close();
        resultSet.close();

        statement.close();
        return ingradientDishes;
    }

    public float getPriceInDish(String nameIngradient, float massa, String typeMassa) throws SQLException {
        float price = 0;
        float maass=0, priceIngradient=0;
        maass = typeMass(typeMassa, massa);
        
        priceIngradient = priceIngradientFunction(nameIngradient);
      

        return  maass*priceIngradient;
    }

    public float typeMass(String typeMassa, float massa) {
        float maass =0;
        if(typeMassa.equals("грамм")){
            maass= massa/1000;
        }
        else {
            maass = massa;
        }
        return  maass;
    }

    private float priceIngradientFunction(String nameIngradient) throws SQLException {
        float price =0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select price from ` ingredient` where `name_ ingredient`='"+nameIngradient+"'");
        while(resultSet.next()) {
            price = resultSet.getInt(1);
        }
        connection.close();
        statement.close();
        resultSet.close();
        return price;
    }

    public void addIngradientInDish(int id, float massa, String typeMassa, float priceInDish, int id_dish) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into compositiondish(id_compositiondish_ingradient,quantity, typeweight, priceindish, id_compositiondish_id_dish) values('"+id+"', '"+massa+"', '"+typeMassa+"', '"+priceInDish+"','"+id_dish+"') ");
        connection.close();

    }

    public ObservableList<IngradientDish> showDataDish(int  id_dish) throws SQLException {
        ObservableList<IngradientDish> ingradientDishes = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select id_compositiondish, `name_ ingredient`,quantity,typeweight, priceindish, name_dish from compositiondish,` ingredient`, dish where id_compositiondish_ingradient=`id_ ingredient` and id_compositiondish_id_dish=dish.id and id_compositiondish_id_dish='"+id_dish+"'");
        while(resultSet.next()){

       int id = resultSet.getInt(1);
       String name = resultSet.getString(2);
       float quantity = resultSet.getFloat(3);
       String type = resultSet.getString(4);
       float price = resultSet.getFloat(5);
       ingradientDishes.add(new IngradientDish(id,name,quantity,type,price));
        }

        connection.close();
        resultSet.close();

        statement.close();
        return  ingradientDishes;
    }

    public float showSumDish(int id) throws SQLException {
       float summa =0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select  sum(compositiondish.priceindish) from compositiondish where compositiondish.id_compositiondish_id_dish='"+id+"'");
        while(resultSet.next()){
                summa = resultSet.getFloat(1);
        }
        resultSet.close();
        connection.close();
        statement.close() ;
        return  summa;
    }

    public ObservableList<SkladDIshIngradient> getIngradientsSearch(String search) throws SQLException {
        ObservableList<SkladDIshIngradient> skladDIshIngradientsList = FXCollections.observableArrayList();
              Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select `id_ ingredient`, `name_ ingredient`, price from  ` ingredient` where `name_ ingredient` LIKE '"+search+"%' ");
        while(resultSet.next()){

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            float price = resultSet.getFloat(3);
           skladDIshIngradientsList.add(new SkladDIshIngradient(id,name, price) );
            System.out.println(name);

        }

        connection.close();
        resultSet.close();

        statement.close();
        return skladDIshIngradientsList;
    }

    public void newPriceDish(int id_dish, float sumDish) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update dish set  price='"+sumDish+"' where id='"+id_dish+"'" );
        connection.close();

        statement.close();
    }

    public boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }

    public void deleteItemDish(int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from  compositiondish where id_compositiondish='"+id+"'" );
        connection.close();

        statement.close();
    }

    public void editVesIngradientInDish(float novoe_kolichestvo, int id, float price, int  id_dish) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update compositiondish set quantity='"+novoe_kolichestvo+"', priceindish='"+price+"' where id_compositiondish='"+id+"'" );
        connection.close();

        statement.close();
    }


    public void newPriceDish2(int id_dish, float sumDish) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        //statement.executeUpdate("update compositiondish set quantity='"+novoe_kolichestvo+"', priceindish='"+price+"' where id_compositiondish='"+id+"'" );
        statement.execute("update dish set price='"+sumDish+"' where  id='"+id_dish+"'");
        connection.close();

        statement.close();
    }

    public void updateMarkupPrice(int id_dish) throws SQLException {
        float price = getPriceDish(id_dish);
        int idGroup = getGroup(id_dish);
        float myMarkup = getMarkupGroup(idGroup);
        float markup = getMarkup(myMarkup);

        updateMarkupPriceInDB(price*markup, id_dish);
    }

    private void updateMarkupPriceInDB(float value, int id) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update dish set newprice='"+value+"' where id ='"+id+"'" );
        connection.close();

        statement.close();
    }

    private float getMarkup(float myMarkup) {
        float a = myMarkup/100;
        return 1+a;
    }

    private float getMarkupGroup(int idGroup) throws SQLException {
        float markup=0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select markup from  groupdish where id_groupdish='"+idGroup+"'");
        while(resultSet.next()){

           markup = resultSet.getFloat(1);

        }

        connection.close();
        resultSet.close();

        statement.close();
        return  markup;
    }

    private int getGroup(int id_dish) throws SQLException {
        int id = 0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select id_groupdish from  dish where dish.id='"+id_dish+"'");
        while(resultSet.next()){

            id = resultSet.getInt(1);

        }

        connection.close();
        resultSet.close();

        statement.close();
        return  id;
    }

    private float getPriceDish(int id_dish) throws SQLException {
        float price=0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select price from  dish where dish.id='"+id_dish+"'");
        while(resultSet.next()){

         price = resultSet.getFloat(1);

        }

        connection.close();
        resultSet.close();

        statement.close();
        return  price;
    }


}
