package contollers;

import Functions.FunctionDish;
import Functions.FunctionWaiter;
import Functions.LoadForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Waiter;
import connection.ConnectionToDB;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class ControllerWaiter {
    Waiter temp;
    Date lastClickTime;
    @FXML
    ObservableList<Waiter> observableListWaiter = FXCollections.observableArrayList();
    @FXML
    TableView<Waiter> tableViewWaiter;
    @FXML
    TableColumn<Waiter, Integer> tableViewWaiterId;
    @FXML TableColumn<Waiter, String> tableViewWaiterName;
    @FXML TableColumn<Waiter, String> tableViewWaiterAdress;
    @FXML TableColumn<Waiter, String> tableViewWaiterTelephone;
    @FXML TableColumn<Waiter, String> tableViewWaiterLogin;
    @FXML TableColumn<Waiter, String> tableViewWaiterPassword;
    @FXML TableColumn<Waiter, String> tableViewWaiterRole;
    @FXML TableColumn<Waiter, String> tableViewWaiterAdressCafe;
    @FXML TableColumn<Waiter, String> tableViewWaiterDirector;
    @FXML
    TextField fieldNameWaiter;
    @FXML TextField fieldLoginWaiter;
    @FXML TextField fieldPasswordWaiter;
    @FXML TextField fieldAdressWaiter;
    @FXML TextField fieldTelephoneWaiter;
    @FXML ComboBox<String> role;
    @FXML ComboBox<String> adress;
    @FXML ObservableList<String> roleList = FXCollections.observableArrayList();
    @FXML ObservableList<String> roleAdress = FXCollections.observableArrayList();


    @FXML
    private void initialize() throws SQLException {
        initData();
        tableViewWaiterId.setCellValueFactory(new PropertyValueFactory<Waiter, Integer>("id"));
        tableViewWaiterName.setCellValueFactory(new PropertyValueFactory<Waiter, String>("name"));
        tableViewWaiterLogin.setCellValueFactory(new PropertyValueFactory<Waiter, String>("login"));
        tableViewWaiterAdress.setCellValueFactory(new PropertyValueFactory<Waiter, String>("adress"));
        tableViewWaiterTelephone.setCellValueFactory(new PropertyValueFactory<Waiter, String>("telephone"));
        tableViewWaiterPassword.setCellValueFactory(new PropertyValueFactory<Waiter, String>("password"));
        tableViewWaiterRole.setCellValueFactory(new PropertyValueFactory<Waiter, String>("role"));
        tableViewWaiterAdressCafe.setCellValueFactory(new PropertyValueFactory<Waiter, String>("adresscafe"));
        tableViewWaiterDirector.setCellValueFactory(new PropertyValueFactory<Waiter, String>("director"));

        tableViewWaiter.setItems(observableListWaiter);
    }

    @FXML
    private void handleRowSelectWaiter() throws SQLException {
        FunctionDish functionDish = new FunctionDish();
        Waiter row = tableViewWaiter.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row != temp) {
            temp = row;
            lastClickTime = new Date();


        } else if (row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis
                int id_waiter = row.getId();
                fieldNameWaiter.setText(row.getName());
                fieldAdressWaiter.setText(row.getAdress());
                fieldTelephoneWaiter.setText(row.getTelephone());
                fieldLoginWaiter.setText(row.getLogin());
                fieldPasswordWaiter.setText(row.getPassword());
                role.setValue(row.getRole());
                adress.setValue(row.getAdresscafe());

            } else {
                lastClickTime = new Date();
            }
        }
    }




    private void initData() throws SQLException {
        FunctionWaiter functionWaiter = new FunctionWaiter();
        observableListWaiter.clear();
        roleList = functionWaiter.getRoles();
        roleAdress= functionWaiter.getAdressCafe();
        role.setValue(roleList.get(0));
        role.setItems(roleList);
        adress.setValue(roleAdress.get(0));
        adress.setItems(roleAdress);
        observableListWaiter = functionWaiter.showAllWaiter();
        tableViewWaiter.setItems(observableListWaiter);

    }



    public void createNewWaiter(ActionEvent actionEvent) throws SQLException {
        FunctionWaiter functionWaiter = new FunctionWaiter();

        if (fieldNameWaiter.getText().trim().isEmpty() || fieldLoginWaiter.getText().trim().isEmpty() || fieldPasswordWaiter.getText().trim().isEmpty() || fieldAdressWaiter.getText().trim().isEmpty() || fieldTelephoneWaiter.getText().trim().isEmpty() ) {
            System.out.println("pusto");
                functionWaiter.showAlertEmptyFiel();
        }
        int id = functionWaiter.checkId(fieldNameWaiter.getText(), fieldAdressWaiter.getText(), fieldTelephoneWaiter.getText(), fieldLoginWaiter.getText(), fieldPasswordWaiter.getText());
        if(id == 0){
            observableListWaiter.clear();
           String adresscafe = adress.getSelectionModel().getSelectedItem();
           String rolecafe = role.getSelectionModel().getSelectedItem();
           int id_adress = functionWaiter.getAdressCafeID(adresscafe);
           int id_rolecafe = functionWaiter.getRolesId(rolecafe);

            functionWaiter.addNewWaiter(fieldNameWaiter.getText(), fieldAdressWaiter.getText(), fieldTelephoneWaiter.getText(), fieldLoginWaiter.getText(), fieldPasswordWaiter.getText(), id_adress, id_rolecafe);
            observableListWaiter = functionWaiter.showAllWaiter();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
             alert.setHeaderText("Запись  сущевствует");
           // alert.setContentText("Такая запись  есть уже");

            alert.showAndWait();
        }


        tableViewWaiter.setItems(observableListWaiter);

    }

    public void editDataWaiter(ActionEvent actionEvent) throws SQLException {
        FunctionWaiter functionWaiter = new FunctionWaiter();
        Waiter waiter =tableViewWaiter.getSelectionModel().getSelectedItem();
        observableListWaiter.clear();
        int id_waiter = waiter.getId();
        String name = fieldNameWaiter.getText();
        String adress = fieldAdressWaiter.getText();
        String telephone = fieldTelephoneWaiter.getText();
        String login = fieldLoginWaiter.getText();
        String password = fieldPasswordWaiter.getText();
        String roleValue = role.getSelectionModel().getSelectedItem();
        int idRole = functionWaiter.getIdRole(roleValue) ;
        functionWaiter.updateDataWaiter(id_waiter, name, adress, telephone, login, password, idRole);
        observableListWaiter = functionWaiter.showAllWaiter();
        tableViewWaiter.setItems(observableListWaiter);

    }

    public void deleteDataWaiter(ActionEvent actionEvent) throws SQLException {
        FunctionWaiter functionWaiter = new FunctionWaiter();
        Waiter waiter = tableViewWaiter.getSelectionModel().getSelectedItem();
        int  id = waiter.getId();
        observableListWaiter.clear();
        functionWaiter.deleteWaiter(id);
        observableListWaiter = functionWaiter.showAllWaiter();
        tableViewWaiter.setItems(observableListWaiter);
    }

    public void more(ActionEvent actionEvent) throws IOException {
        String wayFile = "../views/adminrolesandadress.fxml";
        new LoadForm().loadForm(wayFile);
    }
}
