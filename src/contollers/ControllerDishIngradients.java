package contollers;

import Functions.FunctionDish;
import Functions.FunctionIngradient;
import Functions.FunctionsControllerDishIngradients;
import Functions.LoadForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import sample.Controller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class ControllerDishIngradients {
    String nameDish;
    Date lastClickTime;
    SkladDIshIngradient temp;
    IngradientDish temp2;
    ObservableList<SkladDIshIngradient> ingradientDishesList = FXCollections.observableArrayList();
    ObservableList<IngradientDish> ingradientInDish = FXCollections.observableArrayList();
    @FXML
    TableView<IngradientDish> tableDishIngradient;
    @FXML
    TableColumn<IngradientDish, String> tableDishIngradientName;
    @FXML
    TableColumn<IngradientDish, Float> tableDishIngradientVes;
    @FXML
    TableColumn<IngradientDish, Float> tableDishIngradientCount;
    @FXML
    TableColumn<IngradientDish, Float> tableDishIngradientPrice;
    @FXML
    TableColumn<IngradientDish, Integer> tableDishIngradientId;
    @FXML
    TableView<SkladDIshIngradient> tableSkladIngradient;
    @FXML
    TableColumn<SkladDIshIngradient, Integer> tableSkladIngradientId;
    @FXML
    TableColumn<SkladDIshIngradient, String> tableSkladIngradientName;
    @FXML
    TableColumn<SkladDIshIngradient, Float> tableSkladIngradientPrice;
    @FXML
    Label nameDIshFX;
    @FXML
    ComboBox comboList;
    @FXML
    TextField fieldIngradient;
    @FXML
    TextField fieldCount;
    @FXML
    Label fieldNumberDish;
    @FXML
    Label suma;
    @FXML
    TextField searchField;
    @FXML
    TextField editFieldCountDish;




    @FXML
    private void initialize() throws SQLException {

        loadDataSklad();
        tableDishIngradientId.setCellValueFactory(new PropertyValueFactory<IngradientDish, Integer>("id"));
        tableDishIngradientName.setCellValueFactory(new PropertyValueFactory<IngradientDish, String>("nameDish"));
        tableDishIngradientVes.setCellValueFactory(new PropertyValueFactory<IngradientDish, Float>("ves"));
        tableDishIngradientCount.setCellValueFactory(new PropertyValueFactory<IngradientDish, Float>("count"));
        tableDishIngradientPrice.setCellValueFactory(new PropertyValueFactory<IngradientDish, Float>("price"));

        tableSkladIngradientId.setCellValueFactory(new PropertyValueFactory<SkladDIshIngradient, Integer>("id"));
        tableSkladIngradientName.setCellValueFactory(new PropertyValueFactory<SkladDIshIngradient, String>("name"));
        tableSkladIngradientPrice.setCellValueFactory(new PropertyValueFactory<SkladDIshIngradient, Float>("price"));

    }

    private void loadDataSklad() throws SQLException {
        FunctionsControllerDishIngradients functionsControllerDishIngradients = new FunctionsControllerDishIngradients();
        FunctionIngradient functionIngradient = new FunctionIngradient();

        ObservableList<String> foodunitsList = FXCollections.observableArrayList();
        foodunitsList.clear();
        ingradientDishesList.clear();
        foodunitsList = functionIngradient.getFoodUnits();

        String nameUnit = functionIngradient.getNamesUnitDsidh();
        ingradientDishesList = functionsControllerDishIngradients.showDataSkladIngradints();
        tableSkladIngradient.setItems(ingradientDishesList);
        comboList.setValue(nameUnit);
        comboList.setItems(foodunitsList);

        ingradientInDish.clear();

        tableDishIngradient.setItems(ingradientInDish);

    }

    public void getData(int id, String name) throws SQLException {
        FunctionsControllerDishIngradients functionsControllerDishIngradients = new FunctionsControllerDishIngradients();
        this.nameDIshFX.setText(name);
        this.fieldNumberDish.setText(String.valueOf(id));
        ingradientInDish.clear();
        ingradientInDish = functionsControllerDishIngradients.showDataDish(id);
        float sumDish = functionsControllerDishIngradients.showSumDish(id);
        suma.setText(String.valueOf(sumDish));
        tableDishIngradient.setItems(ingradientInDish);

    }

    public void actionDataSklad(MouseEvent mouseEvent) {

        SkladDIshIngradient row = tableSkladIngradient.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row != temp) {
            temp = row;
            lastClickTime = new Date();


        } else if (row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis
                fieldIngradient.setText(row.getName());


            } else {
                lastClickTime = new Date();
            }
        }
    }

    public void addIngredient(ActionEvent actionEvent) throws SQLException {
        FunctionsControllerDishIngradients functionsControllerDishIngradients = new FunctionsControllerDishIngradients();
        SkladDIshIngradient skladDIshIngradient = tableSkladIngradient.getSelectionModel().getSelectedItem();
        int id = skladDIshIngradient.getId();
        int id_dish = Integer.parseInt(fieldNumberDish.getText());
        String nameIngradient = fieldIngradient.getText();
        float massa = Float.parseFloat(fieldCount.getText());
        String typeMassa = String.valueOf(comboList.getSelectionModel().getSelectedItem());
        // String chechTypeMassa = String.valueOf(functionsControllerDishIngradients.typeMass(typeMassa, massa));
        float priceInDish = functionsControllerDishIngradients.getPriceInDish(nameIngradient, massa, typeMassa);
        ingradientInDish.clear();
        functionsControllerDishIngradients.addIngradientInDish(id, massa, typeMassa, priceInDish, id_dish);
        ingradientInDish = functionsControllerDishIngradients.showDataDish(Integer.parseInt(fieldNumberDish.getText()));
        float sumDish = functionsControllerDishIngradients.showSumDish(id_dish);
        suma.setText(String.valueOf(sumDish));

        functionsControllerDishIngradients.newPriceDish(id_dish, sumDish);
        functionsControllerDishIngradients.updateMarkupPrice(id_dish);
        tableDishIngradient.setItems(ingradientInDish);
    }

    public void search(ActionEvent actionEvent) throws SQLException {
        String search = searchField.getText();
        FunctionsControllerDishIngradients functionsControllerDishIngradients = new FunctionsControllerDishIngradients();
        ingradientDishesList.clear();
        ingradientDishesList = functionsControllerDishIngradients.getIngradientsSearch(search);
        tableSkladIngradient.setItems(ingradientDishesList);
    }

    public void deleteItemWithDish(ActionEvent actionEvent) throws SQLException {
        FunctionsControllerDishIngradients functionsControllerDishIngradients = new FunctionsControllerDishIngradients();
        IngradientDish ingradientDish = tableDishIngradient.getSelectionModel().getSelectedItem();
        functionsControllerDishIngradients.deleteItemDish(ingradientDish.getId());
        ingradientInDish.clear();
        ingradientInDish = functionsControllerDishIngradients.showDataDish(Integer.parseInt(fieldNumberDish.getText()));
        int id_dish = Integer.parseInt(fieldNumberDish.getText());
        float sumDish = functionsControllerDishIngradients.showSumDish(id_dish);
        suma.setText(String.valueOf(sumDish));
        tableDishIngradient.setItems(ingradientInDish);
    }

    public void editDateInggradientInDish(ActionEvent actionEvent) throws SQLException {
        FunctionsControllerDishIngradients functionsControllerDishIngradients = new FunctionsControllerDishIngradients();
        IngradientDish ingradientDish = tableDishIngradient.getSelectionModel().getSelectedItem();
        if(!fieldCount.getText().isEmpty()){
            boolean isNumber =  false;
            isNumber = functionsControllerDishIngradients.isNumber(fieldCount.getText());
            System.out.println(isNumber);
            if(isNumber == true) {
                float novoe_kolichestvo = Float.parseFloat(fieldCount.getText());
                ingradientInDish.clear();


                int id_dish = Integer.parseInt(fieldNumberDish.getText());

                String typeMassa = String.valueOf(comboList.getSelectionModel().getSelectedItem());
                // String chechTypeMassa = String.valueOf(functionsControllerDishIngradients.typeMass(typeMassa, massa));
                float priceInDish = functionsControllerDishIngradients.getPriceInDish(fieldIngradient.getText(), Float.parseFloat(fieldCount.getText()), typeMassa);



                functionsControllerDishIngradients.editVesIngradientInDish(novoe_kolichestvo, ingradientDish.getId(), priceInDish, id_dish);
                float sumDish = functionsControllerDishIngradients.showSumDish(id_dish);
                ingradientInDish = functionsControllerDishIngradients.showDataDish(id_dish);
                functionsControllerDishIngradients.newPriceDish2(id_dish,  sumDish);
                tableDishIngradient.setItems(ingradientInDish);
                suma.setText(String.valueOf(sumDish));
                isNumber = false;
            }else{
                System.out.println("v  stroke est simvoly");
                fieldCount.setText("");
            }

        }else{
            System.out.println("pusto");
        }

    }


    @FXML
    public void handdleMouseAction() {
        IngradientDish row = tableDishIngradient.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row != temp2) {
            temp2 = row;
            lastClickTime = new Date();


        } else if (row == temp2) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis
                fieldIngradient.setText(row.getNameDish());
                fieldCount.setText(String.valueOf(row.getCount()));
                comboList.setValue(row.getVes());
                nameDish = row.getNameDish();
                fieldIngradient.setEditable(false);

            } else {
                lastClickTime = new Date();
            }
        }
    }

    public void click(MouseEvent mouseEvent) throws IOException {
        String wayFile = "../views/scladingradients.fxml";
        new LoadForm().loadForm(wayFile);
    }



    public void updateData(MouseEvent mouseEvent) throws IOException {

        String wayFile = "../views/admin.fxml";
        new LoadForm().loadForm(wayFile);
    }
}
