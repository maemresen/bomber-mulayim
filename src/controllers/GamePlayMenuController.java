package controllers;


import Game.GameMap;
import Players.Hero;
import Players.Zombie;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import sample.Main;

import java.io.IOException;

import static sample.Main.getGameInterface;

public class GamePlayMenuController {


    @FXML
    BorderPane gameBackground; //This is a layout which contains game-play scene.

    private GameMap map;
    private Hero hero2;
    private Hero hero;
    private Zombie zombie;
    private Timeline t;

    public void initialize() {

        map = new GameMap(1, 7, 13);

        hero2 = new Hero("Mülayim", 0, 0, 0, false);
        hero = new Hero("Niyazi", (map.getColumn() - 1) * map.getUnit(), (map.getRow() - 1) * map.getUnit(), 1, false);

        zombie = new Zombie();

        map.addPlayer(hero);
        map.addPlayer(hero2);

        t = new Timeline(new KeyFrame(Duration.ZERO), new KeyFrame(Duration.millis(5), e -> {
            if (hero.isDead() || hero2.isDead()) {
                System.out.println("Game over");
                t.stop();
                try {
                    Hero winner = hero.isDead() ? hero2 : hero;
                    Hero looser = hero.isDead() ? hero : hero2;
                    Main.getGameInterface().showGameOverDialog(winner, looser);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }));

        t.setCycleCount(Animation.INDEFINITE);
        t.play();

        gameBackground.setCenter(map.getGameMap());

        System.out.println(hero);
        System.out.println(hero2);

    }

    @FXML
    public void goToHome() throws IOException {
        Main.getMainWindow().setScene(getGameInterface().getMainMenu());
    }

    @FXML
    public void pressed(KeyEvent e) {

        hero.moveMyHero(e, true);
        hero2.moveMyHero(e, true);
        //hero.movePlayer(e);
        //hero2.movePlayer(e);
    }

    @FXML
    public void released(KeyEvent e) {
        hero.moveMyHero(e, false);
        hero2.moveMyHero(e, false);
        //hero.stopPlayer(e);
        //hero2.stopPlayer(e);
    }


}
