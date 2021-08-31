package Functions;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadForm {
    public void loadForm(String wayFile) throws IOException {
        boolean st = false;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(wayFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
        System.out.println("true"+st);
        System.out.println("false"+!st);
    }
}
