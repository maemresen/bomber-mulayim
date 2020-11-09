package controllers;

import Sounds.ButtonClickSound;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;

/**
 * Created by maemr on 7.05.2016.
 */
public class MouseEffects {

    public static void hoverOn(MouseEvent e) {
        String id = ((Control) e.getSource()).getId();
        Button b = ((Button) e.getSource());
        if (id.contains("Server")) {
            b.setStyle
                    ("-fx-background-image: url('/GameTextures/ButtonTextures/clientButtons/clientButtonHover.png');");
        } else {
            b.setStyle
                    ("-fx-background-image: url('/GameTextures/ButtonTextures/" + id + "/" + id + "Hover.png');");
        }

        new ButtonClickSound().playSound();
    }

    public static void hoverOff(MouseEvent e) {
        String id = ((Control) e.getSource()).getId();

        Button b = ((Button) e.getSource());
        if (id.contains("Server")) {
            b.setStyle
                    ("-fx-background-image: url('/GameTextures/ButtonTextures/clientButtons/clientButton.png');");
        } else {
            b.setStyle
                    ("-fx-background-image: url('/GameTextures/ButtonTextures/" + id + "/" + id + ".png');");
        }
    }

}
