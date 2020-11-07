package controllers;

import Players.Player;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import sample.Main;

import java.io.IOException;

import static sample.Main.getGameInterface;

/**
 * Created by maemresen on 08.04.2016.
 */
public class OptionsMenuController {

    @FXML
    public void mute() {


    }

    @FXML
    public void goToHome() throws IOException {

        Main.getMainWindow().setScene(getGameInterface().getMainMenu());

    }

    @FXML
    public void assignKey(Event e) throws IOException {
        Button b = ((Button) e.getSource());

        /*
            If you want to keep your old key
            Don't worry oldKey saves it for you
         */
        String oldKey = b.getText();
        b.setText("...");

        /*
            Your new key that you pressed on the keybourd
         */
        String myNewKey = getGameInterface().showAssignKeyDialog();

        /*
            If you want to cancel, then you must press ESC
         */
        if (myNewKey.equals("ESCAPE")) {
            myNewKey = oldKey;
        }
        setNewKey(b, e, myNewKey);
    }


    private void setNewKey(Button b, Event e, String myNewKey) {

        b.setText(checkIsSpecialChar(myNewKey));
        String id = ((Control) e.getSource()).getId();

        /*
            You have two key sets for control the Player
            Primary and Secondary.
            0 = primary
            1 = secondary
         */
        int cType = primOrSec(id);
        int dir = dir(id);

        /*
            Add key to keySets if it is suitable
            E.g. It checks is new key already assigned
         */
        Player.addKey(cType, dir, myNewKey);
    }

    /*
        Buttons id's are named for this
        E.g. Button id = primDown
        that means prim so primary control
        and Down key for move hero down
     */
    public static int primOrSec(String s) {
        if (s.contains("prim")) {
            return 0;
        }
        return 1;
    }

    public static int dir(String s) {
        return Integer.parseInt(s.charAt(s.length() - 1) + "");
    }

    public static String checkIsSpecialChar(String s) {
        switch (s) {
            case "UP":
                return "↑";
            case "DOWN":
                return "↓";
            case "LEFT":
                return "←";
            case "RIGHT":
                return "→";
            case "ENTER":
                return "⏎";
        }
        return s;
    }

    public static String specialCharToNormal(String s) {
        switch (s) {
            case "↑":
                return "UP";
            case "↓":
                return "DOWN";
            case "←":
                return "LEFT";
            case "→":
                return "RIGHT";
            case "⏎":
                return "ENTER";
        }
        return s;
    }
}
