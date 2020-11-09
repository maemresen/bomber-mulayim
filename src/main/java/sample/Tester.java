package sample;

import Blocks.FrangibleBlock;
import Blocks.GroundBlock;
import Blocks.InfrangibleBlock;
import Players.Hero;
import Players.Zombie;
import PowerUps.AddBomb;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tester extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FrangibleBlock fb = new FrangibleBlock();
        GroundBlock gb = new GroundBlock();
        InfrangibleBlock ib = new InfrangibleBlock();
        Hero h = new Hero("Emre", 1, false);
        Zombie z = new Zombie();
//        Bomb bomb = new Bomb(0,0,new Hero("Emre",1));
        AddBomb ab = new AddBomb(0, 0);
        System.out.println(fb);
        System.out.println(gb);
        System.out.println(ib);
        System.out.println(h);
        System.out.println(z);

        System.out.println(ab);
        Button b = new Button("detonator");
        b.setOnAction(event -> {

        });

        VBox root = new VBox(20, fb, gb, ib, h, z, b, ab);


        Scene s = new Scene(root);

        primaryStage.setScene(s);
        System.out.println("here");


        primaryStage.setTitle("Audio Player 1");
        primaryStage.setWidth(200);
        primaryStage.setHeight(200);
        primaryStage.show();

        // primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
