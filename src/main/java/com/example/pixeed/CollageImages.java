package com.example.pixeed;
import javafx.scene.layout.AnchorPane;
import java.io.FileNotFoundException;
public class CollageImages {

    protected static void HORIZONTOLDOUBLE(AnchorPane imageViewPane) throws FileNotFoundException {
        imageViewPane.getChildren().clear();
        int startx = 10;
        int starty = 10;
        double height = imageViewPane.getHeight() / 2;
        double width = imageViewPane.getWidth();
        AnchorPane anchorPane = new AnchorPane();
        ImageFrame.addCollageFrame(imageViewPane, anchorPane, startx, starty, width, height);
        starty = (int) (anchorPane.getPrefHeight() + 15);
        anchorPane = new AnchorPane();
        ImageFrame.addCollageFrame(imageViewPane, anchorPane, startx, starty, width, height);
    }

    protected static void VERTICALDOUBLE(AnchorPane imageViewPane) throws FileNotFoundException {
        imageViewPane.getChildren().clear();
        int startx = 10;
        int starty = 10;
        double height = imageViewPane.getHeight();
        double width = imageViewPane.getWidth() / 2;
        AnchorPane anchorPane = new AnchorPane();
        ImageFrame.addCollageFrame(imageViewPane, anchorPane, startx, starty, width, height);
        startx = (int) (anchorPane.getPrefWidth() + 30);
        anchorPane = new AnchorPane();
        ImageFrame.addCollageFrame(imageViewPane, anchorPane, startx, starty, width, height);
    }

   
}
