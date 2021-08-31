package contollers;

import Functions.FunctionsRolesAndDataCafe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.CafeData;
import model.Roles;

import java.sql.SQLException;
import java.util.Date;

public class ControllerRolesAndDataCafe {
    ObservableList<Roles> rolesList = FXCollections.observableArrayList();
    ObservableList<CafeData> cafeDataList = FXCollections.observableArrayList();
    ObservableList<String> valuesList= FXCollections.observableArrayList("Роль", "Адрес");
    @FXML
    TableView<Roles> tableViewRoles;
    @FXML
    TableColumn<Roles, Integer> tableViewRolesId;
    @FXML TableColumn<Roles, String> tableViewRolesRole;
    @FXML TableView<CafeData> tableViewCafeAdress;
    @FXML TableColumn<CafeData, Integer> tableViewCafeAdressId;
    @FXML TableColumn<CafeData, String> tableViewCafeAdressAdress;
    @FXML TableColumn<CafeData, String> tableViewCafeAdressDirector;
    @FXML Button btn_add;
    @FXML Button btn_delete;
    @FXML Button btn_edit;
    @FXML TextField field_one;
    @FXML TextField field_two;
    @FXML TextField field_three;
    @FXML
    ComboBox<String> comboList;
    boolean check_one = false, check_two=false;
    Roles temp;
    Date lastClickTime;
    @FXML
    private void initialize() throws SQLException {
        initData();
        tableViewRolesId.setCellValueFactory(new PropertyValueFactory<Roles, Integer>("id"));
        tableViewRolesRole.setCellValueFactory(new PropertyValueFactory<Roles, String>("role"));

        tableViewCafeAdressId.setCellValueFactory(new PropertyValueFactory<CafeData, Integer>("id"));
        tableViewCafeAdressAdress.setCellValueFactory(new PropertyValueFactory<CafeData, String>("adress"));
        tableViewCafeAdressDirector.setCellValueFactory(new PropertyValueFactory<CafeData, String>("directorr"));
        check_one = true;
        btn_edit.setVisible(false);
        btn_delete.setVisible(false);
        field_two.setVisible(false);

        comboList.setItems(valuesList);
        comboList.setValue(valuesList.get(0));

    }

    private void initData() throws SQLException {
        FunctionsRolesAndDataCafe functionsRolesAndDataCafe = new FunctionsRolesAndDataCafe();
        rolesList.clear();
        cafeDataList.clear();
        rolesList = functionsRolesAndDataCafe.getRoles();
        cafeDataList = functionsRolesAndDataCafe.getDataCafe();
        tableViewCafeAdress.setItems(cafeDataList);
        tableViewRoles.setItems(rolesList);
    }

    public void actionTableOne(MouseEvent mouseEvent) {

        Roles row = tableViewRoles.getSelectionModel().getSelectedItem();
        System.out.println(row.getId());
        comboList.setValue(valuesList.get(0));
        check_one = true;
        btn_delete.setVisible(true);
        btn_delete.setLayoutX(76);
        btn_delete.setLayoutY(283);
        if (row == null) return;
        if (row != temp) {
            temp = row;
            lastClickTime = new Date();


        } else if (row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis
               btn_delete.setVisible(true);
                btn_delete.setLayoutX(137);
                btn_delete.setLayoutY(283);
               btn_edit.setVisible(true);
               field_one.setText(row.getRole());
               field_two.setVisible(false);
               comboList.setValue(valuesList.get(0));
            } else {
                lastClickTime = new Date();


            }
        }
    }

    public void add(ActionEvent actionEvent) throws SQLException {
        String fieldOne, fieldTwo;
        FunctionsRolesAndDataCafe functionsRolesAndDataCafe = new FunctionsRolesAndDataCafe();
        if(comboList.getSelectionModel().getSelectedItem().equals(valuesList.get(0))){
            check_one = false;


            if(!field_one.getText().isEmpty()){
                fieldOne = field_one.getText();
                functionsRolesAndDataCafe.addRole(fieldOne);
                rolesList.clear();
                rolesList = functionsRolesAndDataCafe.getRoles();
                tableViewRoles.setItems(rolesList);
            }else{
                System.out.println("Pustoe pole");

            }

        }else if(comboList.getSelectionModel().getSelectedItem().equals(valuesList.get(1))){
            check_two=false;
            if(!field_one.getText().isEmpty() && !field_two.getText().isEmpty()){
                fieldOne = field_one.getText();
                fieldTwo = field_two.getText();
                functionsRolesAndDataCafe.addAdress(fieldOne, fieldTwo);
                cafeDataList.clear();
                cafeDataList = functionsRolesAndDataCafe.getDataCafe();
                tableViewCafeAdress.setItems(cafeDataList);

            }else{
                System.out.println("Zapolnite vse polya");
            }
        }
    }

    public void checkTable(ActionEvent actionEvent) {
        if(comboList.getSelectionModel().getSelectedItem().equals("Роль")){

         field_two.setVisible(false);
        }else{
            field_two.setVisible(true);


        }
    }
CafeData temp2;
    public void actionTableTwo(MouseEvent mouseEvent) {
        int id = 0;
        CafeData row = tableViewCafeAdress.getSelectionModel().getSelectedItem();
        System.out.println(row.getId());
        comboList.setValue(valuesList.get(1));
        btn_delete.setVisible(true);
        btn_delete.setLayoutX(76);
        btn_delete.setLayoutY(283);
        check_two = true;
        if (row == null) return;
        if (row != temp2) {
            temp2 = row;
            lastClickTime = new Date();


        } else if (row == temp2) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis

                btn_delete.setVisible(true);
                btn_delete.setLayoutX(137);
                btn_delete.setLayoutY(283);
                btn_edit.setVisible(true);
                field_two.setVisible(true);
                field_one.setText(row.getAdress());
                field_two.setText(row.getDirectorr());
                comboList.setValue(valuesList.get(1));
            } else {
                lastClickTime = new Date();


            }
        }
    }

    public void edit(ActionEvent actionEvent) throws SQLException {
        String fieldOne,  fieldTwo;
        FunctionsRolesAndDataCafe functionsRolesAndDataCafe = new FunctionsRolesAndDataCafe();
        if(comboList.getSelectionModel().getSelectedItem().equals(valuesList.get(0))){
            if(!field_one.getText().isEmpty()){
                Roles roles = tableViewRoles.getSelectionModel().getSelectedItem();
                fieldOne = field_one.getText();
                functionsRolesAndDataCafe.editDataAdress(fieldOne, roles.getId());
                rolesList.clear();
                rolesList = functionsRolesAndDataCafe.getRoles();
                tableViewRoles.setItems(rolesList);

            }else{
                System.out.println("pustoe pole");
            }
        }else{
            if(!field_one.getText().isEmpty() && !field_two.getText().isEmpty()){
                CafeData cafeData = tableViewCafeAdress.getSelectionModel().getSelectedItem();
                fieldOne = field_one.getText();
                fieldTwo = field_two.getText();
                functionsRolesAndDataCafe.editDataCafe(fieldOne, fieldTwo,  cafeData.getId());
                cafeDataList.clear();
                cafeDataList = functionsRolesAndDataCafe.getDataCafe();
                tableViewCafeAdress.setItems(cafeDataList);

            }else{
                System.out.println("pustoe pole");
            }

        }
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        FunctionsRolesAndDataCafe functionsRolesAndDataCafe = new FunctionsRolesAndDataCafe();
        if(comboList.getSelectionModel().getSelectedItem().equals(valuesList.get(0))){
            Roles roles = tableViewRoles.getSelectionModel().getSelectedItem();
            rolesList.clear();
            functionsRolesAndDataCafe.deleteRole(roles.getId());
            rolesList = functionsRolesAndDataCafe.getRoles();
            tableViewRoles.setItems(rolesList);
        }else{
            CafeData cafeData = tableViewCafeAdress.getSelectionModel().getSelectedItem();
            cafeDataList.clear();
            functionsRolesAndDataCafe.deleteCafeData(cafeData.getId());
            cafeDataList = functionsRolesAndDataCafe.getDataCafe();
            tableViewCafeAdress.setItems(cafeDataList);
        }
    }
}
