package contollers;

import Functions.FunctionDish;
import Functions.FunctionsControllerDishIngradients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Dish;
import model.TypeDish;


import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerDish {
    private ObservableList<Dish> dishObservableList = FXCollections.observableArrayList();
    @FXML
    TableView<Dish> tableDish;
    @FXML
    TableColumn<Dish, Integer> tableDishId;
    @FXML
    TableColumn<Dish, String> tableDishName;
    @FXML TableColumn<Dish, Float> tableDishPrice;
    @FXML TableColumn<Dish, Float> tableDishPriceWithNacenka;
    @FXML TableColumn<Dish, Float> tableDishProcent;
    @FXML
    TextField fieldAddNewDish;

    @FXML TextField fieldEditNameDish;
  @FXML TextField fieldAddNewPriceDish;
  @FXML TextField fieldEditPriceDish;
  @FXML TextField searchFieldDish;
  @FXML TableColumn<Dish, String> tableDishType;
  @FXML ComboBox comboListTypes;
  @FXML TextField fieldNacenka;
  ObservableList<String> typeDishesList = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws SQLException {
        runint();
        tableDishId.setCellValueFactory(new PropertyValueFactory<Dish, Integer>("id"));
        tableDishName.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        tableDishPrice.setCellValueFactory(new PropertyValueFactory<Dish, Float>("price"));
        tableDishType.setCellValueFactory(new  PropertyValueFactory<Dish, String>("type"));
        tableDishPriceWithNacenka.setCellValueFactory(new  PropertyValueFactory<Dish, Float>("pricewithnacenka"));
        tableDishProcent.setCellValueFactory(new PropertyValueFactory<Dish, Float>("procent"));
    }

    private void runint() throws SQLException {
        FunctionDish functionDish = new FunctionDish();
        dishObservableList.clear();
        typeDishesList.clear();
        dishObservableList = functionDish.viewsAllDish();
        typeDishesList = functionDish.getTypesDish();
        comboListTypes.setItems(typeDishesList);
        tableDish.setItems(dishObservableList);
    }

    public void createNewDish(ActionEvent actionEvent) throws SQLException {
        FunctionDish functionDish = new FunctionDish();

        float price = 0;

        String nameDish = fieldAddNewDish.getText();
        String typeDish = String.valueOf(comboListTypes.getSelectionModel().getSelectedItem());
        float markupDish =0;



        int id = functionDish.checkDishInDatabase(nameDish);
        if(nameDish.trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            // alert.setHeaderText("Results:");
            alert.setContentText("Пустое поле");

            alert.showAndWait();
        }else {
            if (id == 0) {
                dishObservableList.clear();
                System.out.println(nameDish);
                int idType = functionDish.getIdType(typeDish);
                functionDish.addNewDish(nameDish, price, idType, markupDish);
                dishObservableList = functionDish.viewsAllDish();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                // alert.setHeaderText("Results:");
                alert.setContentText("Такая запись  есть уже");

                alert.showAndWait();
            }
            tableDish.setItems(dishObservableList);
        }
    }

    public void editNameDish(ActionEvent actionEvent) throws SQLException {
        FunctionDish functionDish = new FunctionDish();
        String newName = fieldAddNewDish.getText();
        String newType = String.valueOf(comboListTypes.getSelectionModel().getSelectedItem());
        int idType = functionDish.getIdType(newType);

        Dish row = tableDish.getSelectionModel().getSelectedItem();
        int id = row.getId();
        dishObservableList.clear();
        functionDish.updateNameDish(id, newName, idType);
        dishObservableList = functionDish.viewsAllDish();
        tableDish.setItems(dishObservableList);
    }
   Dish temp;
    Date lastClickTime;
    @FXML
    private void handleRowSelect() throws SQLException {
        FunctionDish functionDish = new FunctionDish();
        Dish row = tableDish.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row != temp) {
            temp = row;
            lastClickTime = new Date();


        } else if (row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis

             fieldAddNewDish.setText(temp.getName());
             comboListTypes.setValue(temp.getType());
            } else {
                lastClickTime = new Date();
            }
        }
    }


    public void deleteField(ActionEvent actionEvent) throws SQLException {
        Dish row = tableDish.getSelectionModel().getSelectedItem();
        FunctionDish functionDish = new FunctionDish();
        dishObservableList.clear();
        int id = row.getId();
        functionDish.deleteItem(id);
        dishObservableList = functionDish.viewsAllDish();
        tableDish.setItems(dishObservableList);
    }


    public void test(ActionEvent actionEvent) throws IOException, SQLException {
        String wayFile = "../views/addingradienttodish.fxml";
        Dish dish = tableDish.getSelectionModel().getSelectedItem();

        if(!tableDish.getSelectionModel().isEmpty()){
            int id = dish.getId();
            String name = dish.getName();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(wayFile));

            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));

            ControllerDishIngradients controllerDishIngradients = loader.getController();
            controllerDishIngradients.getData(id, name);

            stage.showAndWait();

        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            // alert.setHeaderText("Results:");
            alert.setContentText("Не вибрано  блюдо");

            alert.showAndWait();

        }
    }


    public void searchDish(ActionEvent actionEvent) throws SQLException {
        FunctionDish functionDish = new FunctionDish();
        dishObservableList.clear();
        if(!searchFieldDish.getText().isEmpty()) {
            dishObservableList = functionDish.functionSearchDish(searchFieldDish.getText());
            tableDish.setItems(dishObservableList);
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            // alert.setHeaderText("Results:");
            alert.setContentText("Ненайдено  блюдо");

            alert.showAndWait();
        }
    }
}
