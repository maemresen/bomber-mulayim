package controllers;

import Players.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import sample.Main;

import java.io.IOException;

import static controllers.OptionsMenuController.specialCharToNormal;
import static sample.Main.getGameInterface;


/**
 * Created by maemresen on 07.04.2016.
 */
public class MainMenuController {

    @FXML
    Button playBtn;

    @FXML
    Button playLanBtn;

    @FXML
    Button exitBtn;


    public void initialize() throws IOException {
        setPlayerKeys(0);
        setPlayerKeys(1);
    }


    private void setPlayerKeys(int cType) throws IOException {
        String s;
        if (cType == 0) {
            s = "prim";
        } else {
            s = "sec";
        }
        for (int i = 0; i < 5; i++) {
            Button b = ((Button) getGameInterface().loadOptionsMenu().lookup("#" + s + i));
            String key = b.getText();
            key = specialCharToNormal(key);
            Player.addKey(cType, i, key);
        }
    }

    @FXML
    public void hoverOn(MouseEvent e) {
        MouseEffects.hoverOn(e);
    }

    @FXML
    public void hoverOff(MouseEvent e) {
        MouseEffects.hoverOff(e);
    }

    @FXML
    public void options() throws IOException {

        Main.getMainWindow().setScene(getGameInterface().getOptionsMenu());

    }

    @FXML
    public void mute() {

    }

    @FXML
    public void playGame() throws IOException {
        Main.getMainWindow().setScene(getGameInterface().loadGamePlayMenu());

    }

    @FXML
    public void playLanGame() throws IOException {


    }

    @FXML
    public void exitGame() {
        Platform.exit();
    }

}
