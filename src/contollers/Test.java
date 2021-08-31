package contollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Test implements Initializable {
    @FXML
    Label qw;


    public void getData(String  id){
        this.qw.setText(id);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
