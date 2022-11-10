package com.example.pixeed;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ImageFrame {
    protected static void openFile(ImageView imageView) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select files", "*.jpg", "*.png", "*.jpeg", "*.jfif");
        fileChooser.getExtensionFilters().add(filter);
        List<File> list = fileChooser.showOpenMultipleDialog(null);
        File x = list.get(0);
        Image image = new Image((new FileInputStream(x)));
        imageView.setImage(image);
    }

    public static void centerImage(ImageView imageView, AnchorPane anchorPane) {
        double hig = imageView.getFitHeight();
        double wid = imageView.getFitWidth();
        imageView.setLayoutX((anchorPane.getPrefWidth() - wid) / 2);
        imageView.setLayoutY((anchorPane.getPrefHeight() - hig) / 2);
    }

    protected static void addResizeButton(AnchorPane anchorPane, ImageView imageView) throws FileNotFoundException {
        Button button = new Button();
        ImageView buttonImage = new ImageView(new Image(new FileInputStream
                ("src/main/resources/com/example/softablitz/icons/resizeIcon.png")));
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(100);
        buttonImage.setEffect(colorAdjust);
        buttonImage.setFitWidth(15);
        buttonImage.setFitHeight(15);
        button.setGraphic(buttonImage);

        button.setStyle("-fx-background-color: transparent");
        button.setLayoutX(anchorPane.getPrefWidth() - 20);
        anchorPane.getChildren().add(button);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                anchorPane.setCursor(Cursor.MOVE);
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                anchorPane.setCursor(Cursor.DEFAULT);
            }
        });

        button.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setLayoutX(mouseEvent.getSceneX() - 410);
                button.setLayoutY(mouseEvent.getSceneY() - 150);
                if (mouseEvent.getSceneX() <= anchorPane.getPrefWidth()) {
                    imageView.setFitWidth(mouseEvent.getSceneX());
                }
                if (mouseEvent.getSceneY() <= anchorPane.getPrefHeight()) {
                    imageView.setFitHeight(mouseEvent.getScreenY());
                }
                centerImage(imageView, anchorPane);
            }
        });
    }


    // frame1 : 1770 1355 , 2515  2445
// frame3 : 125 110  , 670 460
// frame4 : 300 330  , 1620 1130
// frame5 : 1800 605 ,  3180 2530
// frame6 : 1650 945 , 2780 2810
// frame2 : 315  80  , 740  675
}
