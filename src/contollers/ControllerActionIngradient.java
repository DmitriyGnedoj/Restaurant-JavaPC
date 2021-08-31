package contollers;

import Functions.FunctionDish;
import Functions.FunctionIngradient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Ingradients;
import model.UnitDish;
import model.Waiter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;


public class ControllerActionIngradient {
    Ingradients temp;
    Date lastClickTime;
    @FXML
    ObservableList<Ingradients> observableListIngradient = FXCollections.observableArrayList();
    @FXML
    TableView<Ingradients> tableIngradients;
    @FXML
    TableColumn<Ingradients, Integer> numberIngradient;
    @FXML
    TableColumn<Ingradients, String> nameIngradient;
    @FXML
    TableColumn<Ingradients, Float> countIngradient;
    @FXML TableColumn<Ingradients, String> unitIngradient;
    @FXML TableColumn<Ingradients, Float> priceIngradient;

    @FXML ComboBox combounit;
    @FXML
    TextField addNameIngradient;
    @FXML TextField addCountIngradient;
    @FXML TextField addPriceCount;
    @FXML TextField fieldSearch;
    @FXML
    Label fieldNumberDish;


    @FXML
    private void initialize() throws SQLException, IOException {

        numberIngradient.setCellValueFactory(new PropertyValueFactory<Ingradients, Integer>("id"));
        nameIngradient.setCellValueFactory(new PropertyValueFactory<Ingradients, String>("name"));
        countIngradient.setCellValueFactory(new PropertyValueFactory<Ingradients, Float>("count"));
        unitIngradient.setCellValueFactory(new PropertyValueFactory<Ingradients, String>("unit"));
        priceIngradient.setCellValueFactory(new PropertyValueFactory<Ingradients, Float>("price"));
        tableIngradients.setItems(observableListIngradient);
        initData();

    }



    private void initData() throws SQLException {
        FunctionIngradient  functionIngradient = new FunctionIngradient();
        ObservableList<String> foodunitsList =  FXCollections.observableArrayList();
        foodunitsList.clear();
        observableListIngradient.clear();
        observableListIngradient = functionIngradient.showAllIngradients();
        foodunitsList = functionIngradient.getFoodUnits();

        String nameUnit = functionIngradient.getNamesUnitDsidh();

        tableIngradients.setItems(observableListIngradient);
        combounit.setValue(nameUnit);
        combounit.setItems(foodunitsList);


    }

    public void addNewIngradient(ActionEvent actionEvent) throws SQLException {
        int id_unit = 0;
        FunctionIngradient functionIngradient = new FunctionIngradient();
        String name = addNameIngradient.getText();
        float count = Float.parseFloat(addCountIngradient.getText());
        float price = Float.parseFloat(addPriceCount.getText());
        observableListIngradient.clear();
        String unit = String.valueOf(combounit.getSelectionModel().getSelectedItem());
        id_unit = functionIngradient.getIdUnit(unit);
        functionIngradient.createNewIngradient(name,count, price, id_unit);
        observableListIngradient = functionIngradient.showAllIngradients();
        tableIngradients.setItems(observableListIngradient);



    }

    @FXML
    private void handleRowSelectWaiter() throws SQLException {
       // FunctionDish functionDish = new FunctionDish();
        Ingradients row = tableIngradients.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row != temp) {
            temp = row;
            lastClickTime = new Date();


        } else if (row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis
               int id = row.getId();
               addNameIngradient.setText(row.getName());
               addCountIngradient.setText(String.valueOf(row.getCount()));
               addPriceCount.setText(String.valueOf(row.getPrice()));
               combounit.setValue(row.getUnit());


            } else {
                lastClickTime = new Date();
            }
        }
    }


    public void editDataIngradient(ActionEvent actionEvent) throws SQLException {
        FunctionIngradient functionIngradient = new FunctionIngradient();
        Ingradients row = tableIngradients.getSelectionModel().getSelectedItem();
        String name = addNameIngradient.getText();
        float count = Float.parseFloat(addCountIngradient.getText());
        float price = Float.parseFloat(addPriceCount.getText());
         int  id_edt = row.getId();
         int  id_unit = functionIngradient.getIdUnit(String.valueOf(combounit.getSelectionModel().getSelectedItem()));
        observableListIngradient.clear();
       functionIngradient.dataIngradient(id_edt, name, count,  price,id_unit);
       observableListIngradient = functionIngradient.showAllIngradients();
       tableIngradients.setItems(observableListIngradient);
    }

    public void deleteRowIngradient(ActionEvent actionEvent) throws SQLException {
        FunctionIngradient functionIngradient = new FunctionIngradient();
        Ingradients row = tableIngradients.getSelectionModel().getSelectedItem();
        int  id = row.getId();
        observableListIngradient.clear();
        functionIngradient.deleteIngradient(id);
        observableListIngradient = functionIngradient.showAllIngradients();
        tableIngradients.setItems(observableListIngradient);
    }

    public void searchIngredient(ActionEvent actionEvent) throws SQLException {
        FunctionIngradient functionIngradient = new FunctionIngradient();
        String search = fieldSearch.getText();
        observableListIngradient.clear();
        observableListIngradient =  functionIngradient.searchData(search);
        tableIngradients.setItems(observableListIngradient);
    }
}
