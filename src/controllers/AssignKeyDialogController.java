package controllers;

import Players.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by maemr on 21.05.2016.
 */
public class AssignKeyDialogController {

    /*
        You have to press keyboard one time
        This save this.

     */
    private boolean isPressed;


    @FXML
    BorderPane rootBorderPane;

    /*
        This Label shows your key
     */
    @FXML
    Label yourKey;

    public void initialize() {
        rootBorderPane.setFocusTraversable(true);
        isPressed = false;
    }

    @FXML
    public void keyDetected(KeyEvent e) {

        if (!isPressed) {
            isPressed = true;

            //Key that you press
            String keyCode = e.getCode().toString();

            /*
                If it is assigned for another control
                then it is waiting new key from you.
            */
            if (Player.isContains(keyCode)) {
                yourKey.setText("Already assigned\nWaiting...");
                isPressed = false;
            } else {
                yourKey.setText(keyCode);
                ((Stage) rootBorderPane.getScene().getWindow()).close();
            }
        }
    }

}
