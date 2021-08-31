package contollers;

import Functions.FunctionDish;
import Functions.FunctionWaiter;
import Functions.LoadForm;
import Functions.LoginDataFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.LoginForm;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Login {
    ObservableList<LoginForm> loginForms = FXCollections.observableArrayList();
    ObservableList<String> rolesList = FXCollections.observableArrayList();
    @FXML
    TextField login;
    @FXML TextField password;
    String admin = "../views/sample.fxml";
    String waiter="asfasf";
    String shef="../views/ordersaction.fxml";
@FXML
    ComboBox<String> roles;
    @FXML
    private void initialize() throws SQLException {
        initData();
    }

    private void initData() throws SQLException {
        LoginDataFunction loginDataFunction = new LoginDataFunction();
        FunctionWaiter functionWaiter = new FunctionWaiter();
        rolesList.clear();
        rolesList = functionWaiter.getRoles();

        loginForms.clear();
        loginForms = loginDataFunction.getDataUsers();



    }

    public void clickEnter(ActionEvent actionEvent) throws SQLException, IOException {

        LoginDataFunction loginDataFunction = new LoginDataFunction();
        String status = loginDataFunction.checkLoginData(login.getText(), password.getText());
        if(status.equals("Администратор")){
            new LoadForm().loadForm(admin);
        }else if(status.equals("Официант")){
            new LoadForm().loadForm(waiter);
        }else if(status.equals("Шеф-повар")){
            new LoadForm().loadForm(shef);
        }else{
            System.out.println("неправи");
        }

    }
}
