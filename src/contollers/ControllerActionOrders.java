package contollers;

import Functions.DateScope;
import Functions.FunctionDish;
import Functions.FunctionsActionOrders;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.OrderAction;
import model.Waiter;


import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControllerActionOrders {
    ObservableList<OrderAction> orderActionObservableList = FXCollections.observableArrayList();
    Date lastClickTime;
    OrderAction temp;
    @FXML
    TableView<OrderAction> tableActionOrders;

    @FXML TableColumn<OrderAction, Integer> tableActionOrdersId;
    @FXML TableColumn<OrderAction, String> tableActionOrdersDish;
    @FXML TableColumn<OrderAction, Integer> tableActionOrdersQantity;
    @FXML TableColumn<OrderAction, Integer> tableActionOrdersTable;
    @FXML TableColumn<OrderAction, String> tableActionOrdersWaiter;
    @FXML TableColumn<OrderAction, String> tableActionOrdersDate;
    @FXML TableColumn<OrderAction, String> tableActionOrdersTime;
    @FXML
    TextField fieldDoneOrder;


    @FXML
    private void initialize() throws SQLException {

        initData();

        checkStatusOrder();
        tableActionOrdersId.setCellValueFactory(new PropertyValueFactory<OrderAction, Integer>("id"));
        tableActionOrdersDish.setCellValueFactory(new PropertyValueFactory<OrderAction, String>("dish"));
        tableActionOrdersQantity.setCellValueFactory(new PropertyValueFactory<OrderAction, Integer>("quantity"));
        tableActionOrdersTable.setCellValueFactory(new PropertyValueFactory<OrderAction, Integer>("id_table"));
        tableActionOrdersWaiter.setCellValueFactory(new PropertyValueFactory<OrderAction, String>("nameWaiter"));
        tableActionOrdersDate.setCellValueFactory(new PropertyValueFactory<OrderAction, String>("date"));
        tableActionOrdersTime.setCellValueFactory(new PropertyValueFactory<OrderAction, String>("time"));
        tableActionOrders.setItems(orderActionObservableList);


    }

    private int getCountOrders() throws SQLException {
        int orders;
        FunctionsActionOrders functionsActionOrders = new FunctionsActionOrders();
        orders = functionsActionOrders.getCountOrders();
        return orders;
    }

    private void initData() throws SQLException {
        FunctionsActionOrders functionsActionOrders = new FunctionsActionOrders();
        orderActionObservableList.clear();
        orderActionObservableList = functionsActionOrders.showActionOrders();

        tableActionOrders.setItems(orderActionObservableList);

    }



    private void checkStatusOrder(){
        FunctionsActionOrders functionsActionOrders = new FunctionsActionOrders();

        new Thread(new Runnable() {
            public void run() {
                while(true) { //бесконечно крутим

                    try {
                        Thread.sleep(10000); // 4 секунды в милисекундах
                            int status = getCountOrders();

                        if (status>0 && status < 100) {
                            show();



                        }
                        else{
                            System.out.println("Пока заказов  не  было");
                        }
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void show() throws SQLException {
        FunctionsActionOrders functionsActionOrders = new FunctionsActionOrders();
        orderActionObservableList.clear();
        orderActionObservableList = functionsActionOrders.showActionOrders();
        //AudioClip audioClip = new AudioClip(Paths.get("src/sound/sound.mp3").toUri().toString());
        // audioClip.play();
         tableActionOrders.setItems(orderActionObservableList);
    }


    @FXML
    private void handleRowSelect() throws SQLException {
        FunctionsActionOrders functionsActionOrders = new FunctionsActionOrders();
        OrderAction row = tableActionOrders.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row != temp) {
            temp = row;
            lastClickTime = new Date();


        } else if (row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis

                fieldDoneOrder.setText(String.valueOf(row.getId()));
            } else {
                lastClickTime = new Date();
            }
        }
    }

    public void odrerDone(ActionEvent actionEvent) throws SQLException {
        ObservableList<OrderAction> orderActionsList = FXCollections.observableArrayList();
        FunctionsActionOrders functionsActionOrders = new FunctionsActionOrders();
        DateScope dateScope = new DateScope();
        String date = dateScope.getDAteForScope();
        int id_order = Integer.parseInt(fieldDoneOrder.getText());
        float sumCheck = functionsActionOrders.getPricesDishes(id_order);
        functionsActionOrders.addInScope(sumCheck, id_order,date);
        orderActionsList = functionsActionOrders.getOrderById(id_order, orderActionObservableList);
        functionsActionOrders.updateIngradientsCountOnSclad(id_order, orderActionsList);
        orderActionObservableList.clear();
         functionsActionOrders.updateDoneOrder(id_order);
        orderActionObservableList = functionsActionOrders.showActionOrders();
        tableActionOrders.setItems(orderActionObservableList);
    }
}
