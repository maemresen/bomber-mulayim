package Layouts;

import Backgrounds.BubbleBackground;
import Players.Hero;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by maemresen on 08.04.2016.
 */
public class GameInterface {

    /*
    --------------------------------------------------------------------------------------
            * These are all of the scenes that game contains.
    --------------------------------------------------------------------------------------
     */

    /*
        load methods for as you know for loading fxml file.
        If you load fxml file one time then you do not need to load it again.
        So, I use get method to get fxml files that loaded before
     */

    private Scene mainMenu;
    private Scene gamePlayMenu;
    private Scene optionsMenu;

    /*
        server menu host menu and lobby is not working
        because we are not making the lan version of the game
        but maybe we will work on it so I didn't delete them
     */


    private double xOffset;
    private double yOffset;


    public GameInterface() throws IOException {

    }


    /*
        To move scene with undecorated stage
        Scene follows the mouse.
        So you can drag it when you want by clicking anywhere of the scene
     */
    public void move(Scene scene) {

        scene.setOnMousePressed(event -> {

            xOffset = event.getSceneX();
            yOffset = event.getSceneY();

        });
        scene.setOnMouseDragged(event -> {

            Main.getMainWindow().setX(event.getScreenX() - xOffset);
            Main.getMainWindow().setY(event.getScreenY() - yOffset);

        });

    }

    /*
    --------------------------------------------------------------------------------------
            * To load fxml files that we created with scene builder
    --------------------------------------------------------------------------------------
     */
    private Scene load(String url) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(url + ".fxml"));
        Scene s;
        if (url.equals("MainMenu")) {
            Main.setMainMenuBubbleBack(new BubbleBackground(root));
            s = new Scene(Main.getMainMenuBubbleBack());
        } else {
            s = new Scene(root);
        }
        s.setFill(Color.BLACK);
        move(s);
        printScene(s, url);
        return s;
    }

    private void printScene(Scene scene, String name) {
        WritableImage image = scene.snapshot(null);
        // TODO: probably use a file chooser here
        File file = new File(name + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

    public Scene loadMainMenu() throws IOException {
        mainMenu = load("MainMenu");
        return mainMenu;
    }

    public void setMainMenu(Scene mainMenu) {
        this.mainMenu = mainMenu;
    }

    public Scene loadGamePlayMenu() throws IOException {
        gamePlayMenu = load("GamePlayMenu");
        return gamePlayMenu;
    }

    public void setGamePlayMenu(Scene gamePlayMenu) {
        this.gamePlayMenu = gamePlayMenu;
    }


    public Scene loadOptionsMenu() throws IOException {
        optionsMenu = load("OptionsMenu");
        return optionsMenu;
    }

    public void setOptionsMenu(Scene optionsMenu) {
        this.optionsMenu = optionsMenu;
    }

    /*
        This layout is a bit different than others
        Because this is pop-up window. To assign key
        from options menu for control the player.
        This scene wait a key from you and if it
        gets the key from you it close and returns
        your key as String
     */
    public String showAssignKeyDialog() throws IOException {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        Scene scene = load("AssignKeyDialog");
        window.setScene(scene);
        window.showAndWait();
        return ((Label) scene.lookup("#yourKey")).getText();
    }

    public void showGameOverDialog(Hero winner, Hero looser) throws IOException {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.TRANSPARENT);
        Scene scene = load("GameOverDialog");
        ((ImageView) scene.lookup("#winnerView")).setImage(winner.getImage());
        ((ImageView) scene.lookup("#looserView")).setImage(looser.getImage());
        ((Label) scene.lookup("#winnerName")).setText(winner.getObjName());
        ((Label) scene.lookup("#looserName")).setText(looser.getObjName());

        window.setScene(scene);
        window.show();


    }

    public Scene getMainMenu() {
        return mainMenu;
    }

    public Scene getGamePlayMenu() {
        return gamePlayMenu;
    }

    public Scene getOptionsMenu() {
        return optionsMenu;
    }

}
