package contollers;

import Functions.AdminTypesANDMarkufFunctions;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Dish;
import model.Markup;
import model.Type;

import javax.swing.plaf.basic.BasicBorders;
import java.sql.SQLException;
import java.util.Date;

public class AdminTypesAndMarkup {
    @FXML
    ComboBox comboList;
    @FXML
    TableView<Markup> tableViewGroupAndPrices;

    @FXML
    TableColumn<Markup, Integer> tableViewGroupAndPricesId;
    @FXML
    TableColumn<Markup, String> tableViewGroupAndPricesType;
    @FXML
    TableColumn<Markup, Float> tableViewGroupAndPricesMarkup;
    @FXML
    TableView<Type> tableViewFoodUnit;
    @FXML
    TableColumn<Type, Integer> tableViewFoodUnitId;
    @FXML
    TableColumn<Type, String> tableViewFoodUnitType;
    ObservableList<Type> typeList = FXCollections.observableArrayList();
    ObservableList<Markup> markuplist = FXCollections.observableArrayList();
    @FXML
    TextField textField1;
    @FXML
    TextField textField2;

    @FXML
    Button btn1;
    @FXML
    Button btn2;
    @FXML Button btn3;
    boolean check_one = false, isCheck_two = false, check_edit=false, check_delete = false;

    ObservableList<String> list = FXCollections.observableArrayList("Блюдо", "Измерение");

    @FXML
    private void initialize() throws SQLException {
        tableViewGroupAndPricesId.setCellValueFactory(new PropertyValueFactory<Markup, Integer>("id"));
        tableViewGroupAndPricesType.setCellValueFactory(new PropertyValueFactory<Markup, String>("type"));
        tableViewGroupAndPricesMarkup.setCellValueFactory(new PropertyValueFactory<Markup, Float>("markup"));
        tableViewFoodUnitId.setCellValueFactory(new PropertyValueFactory<Type, Integer>("id"));
        tableViewFoodUnitType.setCellValueFactory(new PropertyValueFactory<Type, String>("type"));
        btn2.setVisible(false);
        btn3.setVisible(false);
        check_one = true;

        getData();
        //visibleFieldsFalse();
    }


    private void getData() throws SQLException {
        AdminTypesANDMarkufFunctions adminTypesANDMarkufFunctions = new AdminTypesANDMarkufFunctions();
        comboList.setItems(list);
        comboList.setValue("Блюдо");
        typeList.clear();
        ;
        markuplist.clear();
        typeList = adminTypesANDMarkufFunctions.getTypes();
        markuplist = adminTypesANDMarkufFunctions.getMarkups();
        tableViewGroupAndPrices.setItems(markuplist);
        tableViewFoodUnit.setItems(typeList);

    }

    Markup temp;
    Date lastClickTime;

    public void actionTableOne(MouseEvent mouseEvent) {
        check_one = true;
        textField2.setVisible(true);
        Markup row = tableViewGroupAndPrices.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row != temp) {
            temp = row;
            lastClickTime = new Date();


        } else if (row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis
                textField1.setText(row.getType());
                textField2.setText(String.valueOf(row.getMarkup()));
                btn3.setVisible(true);
                btn2.setVisible(true);
                comboList.setValue(list.get(0));
                check_edit = true;
                check_delete=true;
            } else {
                lastClickTime = new Date();
            }
        }
    }

    Type temp2;

    public void actionTableTwo(MouseEvent mouseEvent) {

        isCheck_two = true;

        textField1.setVisible(true);
        textField2.setVisible(false);

        Type row = tableViewFoodUnit.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row != temp2) {
            temp2 = row;
            lastClickTime = new Date();



        } else if (row == temp2) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis
                textField1.setText(row.getType());
                btn3.setVisible(true);
                btn2.setVisible(true);
                comboList.setValue(list.get(1));
            } else {
                lastClickTime = new Date();
            }
        }
    }

    public void create(ActionEvent actionEvent) throws SQLException {
        float markup = 0;
        String type;
        AdminTypesANDMarkufFunctions adminTypesANDMarkufFunctions = new AdminTypesANDMarkufFunctions();
        if (check_one == true) {
            check_one = false;
            type = textField1.getText();
            markup = Float.parseFloat(textField2.getText());
            adminTypesANDMarkufFunctions.createNewMarkup(type, markup);
        } else {

            type = textField1.getText();
            adminTypesANDMarkufFunctions.createNewFoodUnit(type);
        }
        typeList.clear();
        markuplist.clear();
        typeList = adminTypesANDMarkufFunctions.getTypes();
        markuplist = adminTypesANDMarkufFunctions.getMarkups();
        tableViewFoodUnit.setItems(typeList);
        tableViewGroupAndPrices.setItems(markuplist);
    }

    public void edit(ActionEvent actionEvent) throws SQLException {
        AdminTypesANDMarkufFunctions adminTypesANDMarkufFunctions = new AdminTypesANDMarkufFunctions();

        String name;
        float markupValue;
        if(check_edit==true){
            check_edit = false;
            Markup markup = tableViewGroupAndPrices.getSelectionModel().getSelectedItem();

            name = textField1.getText();
            markupValue = Float.parseFloat(textField2.getText());
            markuplist.clear();
            adminTypesANDMarkufFunctions.editMarkup(markup.getId(), name, markupValue);
            markuplist = adminTypesANDMarkufFunctions.getMarkups();
            tableViewGroupAndPrices.setItems(markuplist);
        }else if (isCheck_two == true){
            isCheck_two = false;
            Type type = tableViewFoodUnit.getSelectionModel().getSelectedItem();
            name = textField1.getText();
            typeList.clear();
            adminTypesANDMarkufFunctions.editType(type.getId(), name);
            typeList = adminTypesANDMarkufFunctions.getTypes();
            tableViewFoodUnit.setItems(typeList);

        }

    }


    public void chrckValue(ActionEvent actionEvent) throws SQLException {
        AdminTypesANDMarkufFunctions adminTypesANDMarkufFunctions = new AdminTypesANDMarkufFunctions();
        float markup = 0;
        String type;
        if (comboList.getSelectionModel().getSelectedItem().equals("Измерение")) {
            textField2.setVisible(false);
            System.out.println(1);
        } else {
            textField2.setVisible(true);
            check_one = true;
            System.out.println(2);
        }


    }

    public void deleteItem(ActionEvent actionEvent) throws SQLException {
        AdminTypesANDMarkufFunctions adminTypesANDMarkufFunctions = new AdminTypesANDMarkufFunctions();
        if(check_delete==true){
           // check_delete = false;
            Markup markup = tableViewGroupAndPrices.getSelectionModel().getSelectedItem();
            adminTypesANDMarkufFunctions.deleteValueMarkup(markup.getId());
            markuplist.clear();
            markuplist = adminTypesANDMarkufFunctions.getMarkups();
            tableViewGroupAndPrices.setItems(markuplist);
        }else if (isCheck_two == true){
            isCheck_two = false;
            Type type = tableViewFoodUnit.getSelectionModel().getSelectedItem();
            adminTypesANDMarkufFunctions.deleteValueFoodUnit(type.getId());
            typeList.clear();
            typeList = adminTypesANDMarkufFunctions.getTypes();
            tableViewFoodUnit.setItems(typeList);
        }
    }
}
