package Backgrounds;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by maemr on 15.05.2016.
 */
public class BubbleBackground extends Group {


    private HashMap<Path, Boolean> paths;
    private List<Path> myPaths;
    private double width, height;
    private Timeline addBubble;

    public BubbleBackground(Node front) {

        paths = new HashMap<>();
        myPaths = new ArrayList<>();


        this.getChildren().add(front);

        addBubble = new Timeline(new KeyFrame(Duration.ZERO, event -> nodeGenerator()),
                new KeyFrame(Duration.seconds(3)));
        addBubble.setCycleCount(Animation.INDEFINITE);

        width = ((Pane) front.lookup("#mainMenuPane")).getMaxWidth();
        height = ((Pane) front.lookup("#mainMenuPane")).getMaxHeight();
        initPaths();
        addBubble.play();
    }

    private void initPaths() {


        int w = ( (int) (width + 60) );

        /*

                \            \
        Path --> \            \
                  \ <--------->\
                      spacing
         */
        int spacing = w / 15;

        for (int i = 0; i <= w; i += spacing) {
            for (int j = 0; j <= w; j += spacing) {
                Path p = getMyPath(i, j);
                paths.put(p, false);

            }
        }

        myPaths = new ArrayList<>(paths.keySet());

    }

    private void nodeGenerator() {


        Path p = myPaths.get(new Random().nextInt(myPaths.size()));

        //If there is a bubble on the path It doesn't put new bubble
        if (!paths.get(p)) {
            paths.put(p, true);
            //Bubble Texture
            Node n = new ImageView("/GameTextures/BubbleBackgroundTextures/bubble.png");

            n.setVisible(false);
            n.setLayoutX(-50);
            this.getChildren().add(n);
            n.toBack();
            PathTransition pt = getPathTransition(n, p);
            pt.play();
            n.setVisible(true);
            pt.setOnFinished(event -> {
                paths.put(p, false);
            });
        }


    }

    private PathTransition getPathTransition(Node node, Path path) {

        Random rand = new Random();
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.seconds(new Random().nextInt(15) + 5));
        pt.setDelay(Duration.seconds(.5));
        pt.setNode(node);

        pt.setPath(path);
        pt.setCycleCount(Animation.INDEFINITE);
        return pt;
    }

    private Path getMyPath(int sPoint, int ePoint) {
        Path path = new Path();
        LineTo lineTo = new LineTo();
        lineTo.setX(ePoint);
        lineTo.setY(-30);

        MoveTo moveTo = new MoveTo();
        moveTo.setX(sPoint-30);
        moveTo.setY(height + 30);

        path.getElements().addAll(moveTo, lineTo);
        path.setStrokeWidth(1);
        path.setStroke(Color.BLACK);

        return path;
    }


    public void stopBubbles() {
        addBubble.stop();
    }

}
