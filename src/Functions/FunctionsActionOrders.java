package Functions;

import connection.ConnectionToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.NewCountIngradient;
import model.OrderAction;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FunctionsActionOrders {

    public ObservableList<OrderAction> showActionOrders() throws SQLException {
        ObservableList<OrderAction> orderActionObservableList = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT actionorders.id, dish.name_dish,actionorders.count, tableid , user.waiter, status, actionorders.date, actionorders.time FROM actionorders,user,dish where actionorders.id_dish=dish.id  and actionorders.id_waiter=user.id and  status!=2");
        while (resultSet.next()) {

            int id = resultSet.getInt(1);
            String dish = resultSet.getString(2);
            int amount = resultSet.getInt(3);
            int id_table = resultSet.getInt(4);
            String nameWaiter = resultSet.getString(5);
            int status = resultSet.getInt(6);
            String date = resultSet.getString(7);
            String time= resultSet.getString(8);
            orderActionObservableList.add(new OrderAction(id, dish, amount, id_table, nameWaiter, date, time));
            // System.out.println(id+" "+name);

        }
        connection.close();

        statement.close();
        resultSet.close();
        return orderActionObservableList;
    }

    public int getCountOrders() throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM actionorders where status=0");
        int counter = 0;
        if (resultSet.next()) {
            counter = resultSet.getInt(1);
        }
        connection.close();

        statement.close();

        return counter;
    }

    public void updateStatus() throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update actionorders set status=1 where status=0");
        connection.close();
        statement.close();
    }

    boolean st = false;
    public void updateDoneOrder(int id_order) throws SQLException {

        if (st == false) {
            Connection connection = new ConnectionToDB().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update actionorders set status=2 where id='" + id_order + "'");
            connection.close();
            statement.close();
        }else{
            System.out.println("ЧЯто  ото не  так");
            st = false;
        }
    }

    public ObservableList<OrderAction> showActionOrdersNotDone() throws SQLException {
        ObservableList<OrderAction> orderActionObservableList = FXCollections.observableArrayList();
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT actionorders.id, dish.name_dish,actionorders.count, number_table.number, waiter.waiter,status,actionorders.date, actionorders.time FROM actionorders,waiter,number_table,dish where actionorders.id_dish=dish.id and actionorders.id_table=number_table.id and actionorders.id_waiter=waiter.id and status!=2");
        while (resultSet.next()) {

            int id = resultSet.getInt(1);
            String dish = resultSet.getString(2);
            int amount = resultSet.getInt(3);
            int id_table = resultSet.getInt(4);
            String nameWaiter = resultSet.getString(5);
            String date = resultSet.getString(6);
            String time= resultSet.getString(7);
            orderActionObservableList.add(new OrderAction(id, dish, amount, id_table, nameWaiter, date,  time));
            // System.out.println(id+" "+name);

        }
        connection.close();

        statement.close();
        resultSet.close();
        return orderActionObservableList;

    }

    public float getPricesDishes(int id_order) throws SQLException {
        float sum = 0;
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select sum(newprice*actionorders.count) from dish, actionorders where actionorders.id_dish=dish.id and actionorders.id='" + id_order + "';");
        while (resultSet.next()) {
            sum = resultSet.getFloat(1);
        }
        connection.close();

        statement.close();
        resultSet.close();
        return sum;
    }

    public void addInScope(float sumCheck, int id_order, String date) throws SQLException {
        Connection connection = new ConnectionToDB().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into scope(datescope,pricescope, idorder) values('" + date + "','" + sumCheck + "','" + id_order + "')");
        connection.close();
        statement.close();
    }


    public ObservableList<OrderAction> getOrderById(int id_order, ObservableList<OrderAction> orderActionObservableList) {
        ObservableList<OrderAction> orderActions = FXCollections.observableArrayList();
        System.out.println(orderActionObservableList.size());
        for (int i = 0; i < orderActionObservableList.size(); i++) {
            if (orderActionObservableList.get(i).getId() == id_order) {
                orderActions.add(orderActionObservableList.get(i));
            }
        }
        return orderActions;
    }

    public void updateIngradientsCountOnSclad(int id_order, ObservableList<OrderAction> orderActionsList) throws SQLException {
        ObservableList<NewCountIngradient> countIngradients = FXCollections.observableArrayList();
        ObservableList<NewCountIngradient> countIngradientsSklad = FXCollections.observableArrayList();

        float quantity = 0, countQuantity = 0;
        boolean check = false;


        for (int i = 0; i < orderActionsList.size(); i++) {

            Connection connection = new ConnectionToDB().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select `name_ ingredient`,  quantity, count*1000 from compositiondish, dish, ` ingredient` where id_compositiondish_id_dish=id and id_compositiondish_ingradient=` ingredient`.`id_ ingredient` and  name_dish='" + orderActionsList.get(i).getDish() + "'");
            while (resultSet.next()) {
                check = false;
                countQuantity = resultSet.getFloat(2) * orderActionsList.get(i).getQuantity();
                quantity = resultSet.getFloat(3) - countQuantity;
                //
                countIngradients.add(new NewCountIngradient(resultSet.getString(1), countQuantity,  quantity));
            }
            for(int j=0; j<countIngradients.size(); j++){
                if(countIngradients.get(j).getQuantity() < 0){
                    showAlertNotIngradients(countIngradients.get(j).getName());
                    check = true;
                    countIngradients.clear();
                }

            }
            if(check==false){
                for(int index =0; index< countIngradients.size(); index++){
                    updateCountIngradient(countIngradients.get(index).getQuantity(),countIngradients.get(index).getName());

                }
            }
            countIngradients.clear();
            resultSet.close();
            connection.close();
            statement.close();

        }


    }

    private void updateCountIngradient(float quantity, String string) throws SQLException {
        boolean check = false;
        if(quantity < 0){
            showAlertNotIngradients(string);

        }else {
            Connection connection = new ConnectionToDB().getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("update ` ingredient` set  count ='" + quantity / 1000 + "' where `name_ ingredient`='" + string + "'");
            connection.close();
            statement.close();
        }
    }

    private void showAlertNotIngradients(String s) {
        st=true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка");
        // alert.setHeaderText("Results:");
        alert.setContentText("На складе  не  хватает  инградиентов  для  приготовления  блюд. Не хватает - "+s);

        alert.showAndWait();

    }
}
