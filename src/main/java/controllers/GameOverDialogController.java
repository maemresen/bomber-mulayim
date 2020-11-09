package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;

/**
 * Created by maemr on 13.06.2016.
 */
public class GameOverDialogController {

    @FXML
    VBox rootBox;

    @FXML
    public void goHome() {
        Main.getMainWindow().setScene(Main.getGameInterface().getMainMenu());
        ((Stage) rootBox.getScene().getWindow()).close();
    }
}
