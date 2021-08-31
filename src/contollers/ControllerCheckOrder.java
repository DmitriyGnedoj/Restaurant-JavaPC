package contollers;

import Functions.FunctionsChecks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.CheckOrderModel;
import model.Checks;

import java.sql.SQLException;

public class ControllerCheckOrder {
    int id = 0;
    ObservableList<CheckOrderModel> checksList = FXCollections.observableArrayList();
    @FXML
    TableView<CheckOrderModel> checkOrderTable;
    @FXML
    TableColumn<CheckOrderModel, Integer> checkOrderId;
    @FXML TableColumn<CheckOrderModel, String> checkOrderDate;
    @FXML TableColumn<CheckOrderModel, String> checkOrderTime;
    @FXML TableColumn<CheckOrderModel, Float> checkOrderSum;
    @FXML TableColumn<CheckOrderModel, Float> checkOrderSumAll;
@FXML
    Text check;
    @FXML

    private void initialize() throws SQLException {

        loadData();
        checkOrderId.setCellValueFactory(new PropertyValueFactory<CheckOrderModel, Integer>("id"));
        checkOrderDate.setCellValueFactory(new PropertyValueFactory<CheckOrderModel, String>("date"));
        checkOrderTime.setCellValueFactory(new PropertyValueFactory<CheckOrderModel, String>("time"));
        checkOrderSum.setCellValueFactory(new PropertyValueFactory<CheckOrderModel, Float>("sum"));
        checkOrderSumAll.setCellValueFactory(new PropertyValueFactory<CheckOrderModel, Float>("sumall"));

    }



    public void getData(int id) throws SQLException {
        this.check.setText(String.valueOf(id));
        checksList.clear();
        int num = Integer.parseInt(check.getText());
        FunctionsChecks functionsChecks = new FunctionsChecks();
        checksList.clear();
        checksList = functionsChecks.getdata(num);
        checkOrderTable.setItems(checksList);
    }
    private void loadData() throws SQLException {


    }
}
