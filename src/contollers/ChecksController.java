package contollers;

import Functions.FunctionDish;
import Functions.FunctionsChecks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Checks;
import model.IngradientDish;
import model.SkladDIshIngradient;
import model.Waiter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class ChecksController {
    Checks temp;
    Date lastClickTime;
    @FXML
    TableView<Checks> checkTable;
    @FXML TableColumn<Checks, Integer> checkId;
    @FXML TableColumn<Checks, String> checkDate;
    @FXML TableColumn<Checks, String> checkTime;
    @FXML TableColumn<Checks, Float> checkSum;
    @FXML
    TextField checkSearch;
    ObservableList<Checks> checksList = FXCollections.observableArrayList();



    @FXML

    private void initialize() throws SQLException {

        loadData();
        checkId.setCellValueFactory(new PropertyValueFactory<Checks, Integer>("id"));
        checkDate.setCellValueFactory(new PropertyValueFactory<Checks, String>("date"));
        checkTime.setCellValueFactory(new PropertyValueFactory<Checks, String>("time"));
        checkSum.setCellValueFactory(new PropertyValueFactory<Checks, Float>("sum"));
      
    }

    private void loadData() throws SQLException {
        FunctionsChecks functionsChecks = new FunctionsChecks();
        checksList.clear();
        checksList = functionsChecks.getDataChecks();
        checkTable.setItems(checksList);
    }


    public void search(ActionEvent actionEvent) throws SQLException {
        checksList.clear();
        FunctionsChecks functionsChecks = new FunctionsChecks();
        String data = checkSearch.getText();
        boolean check = functionsChecks.getSearchData(data);
        if(check==false){
            System.out.println("stroka");
            checksList = functionsChecks.getDataDate(checkSearch.getText());
        }else{
            System.out.println("chislo");
            checksList = functionsChecks.getDataCheckId(Integer.parseInt(checkSearch.getText()));
        }
        checkTable.setItems(checksList);

    }


    @FXML
    private void onClicked() throws SQLException, IOException {
        String wayFile="../views/showordercheck.fxml";
        FunctionDish functionDish = new FunctionDish();
     //   Waiter row = tableViewWaiter.getSelectionModel().getSelectedItem();
        Checks row= checkTable.getSelectionModel().getSelectedItem();
        if (row == null) return;
        if (row != temp) {
            temp = row;
            lastClickTime = new Date();


        } else if (row == temp) {
            Date now = new Date();
            long diff = now.getTime() - lastClickTime.getTime();
            if (diff < 300) { //another click registered in 300 millis
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(wayFile));

                Parent parent = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                ControllerCheckOrder controllerCheckOrder = loader.getController();
                controllerCheckOrder.getData(row.getId());

                stage.showAndWait();
            } else {
                lastClickTime = new Date();
            }
        }
    }
}
