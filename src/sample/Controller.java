package sample;

import Functions.LoadForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Controller {



    public void adminWaiter(ActionEvent actionEvent) throws IOException, SQLException {
        String wayFile = "../views/adminwaiter.fxml";
        new LoadForm().loadForm(wayFile);

    }

    public void adminDish(ActionEvent actionEvent) throws IOException {
        String wayFile = "../views/admindish.fxml";
        new LoadForm().loadForm(wayFile);
    }

    public void showActionOrders(ActionEvent actionEvent) throws IOException {
        String wayFile = "../views/ordersaction.fxml";
        new LoadForm().loadForm(wayFile);
    }



    public void showIngradients(ActionEvent actionEvent) throws IOException {
        String wayFile = "../views/scladingradients.fxml";
        new LoadForm().loadForm(wayFile);
    }

    public void showTypesAndMarkup(ActionEvent actionEvent) throws IOException {
        String wayFile = "../views/admin.fxml";
        new LoadForm().loadForm(wayFile);
    }

    public void showAllChecks(ActionEvent actionEvent) throws IOException {
        String wayFile = "../views/checks.fxml";
        new LoadForm().loadForm(wayFile);
    }
}
