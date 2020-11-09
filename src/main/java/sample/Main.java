package sample;


import Backgrounds.BubbleBackground;
import Layouts.GameInterface;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {



    private static Stage mainWindow;
    private static BubbleBackground mainMenuBubbleBack;
    private static GameInterface gameInterface; //All game scenes are in this class



    @Override
    public void start(Stage primaryStage) throws Exception {

        mainWindow = primaryStage;


        gameInterface = new GameInterface();


        mainWindow.initStyle(StageStyle.TRANSPARENT);
        mainWindow.setScene(gameInterface.loadMainMenu());

        mainWindow.show();


    }

    public static Stage getMainWindow(){
        return mainWindow;
    }

    public static BubbleBackground getMainMenuBubbleBack() {
        return mainMenuBubbleBack;
    }

    public static void setMainMenuBubbleBack(BubbleBackground mainMenuBubbleBack) {
        Main.mainMenuBubbleBack = mainMenuBubbleBack;
    }

    public static void main(String[] args) {

        launch(args);

    }

    public static GameInterface getGameInterface() {
        return gameInterface;
    }
}
