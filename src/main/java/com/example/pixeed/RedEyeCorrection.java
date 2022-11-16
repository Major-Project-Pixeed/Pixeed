package com.example.pixeed;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import static org.opencv.imgproc.Imgproc.dilate;

public class RedEyeCorrection {

    RedEyeCorrection() {

    }



    public static void showResult(Mat img, ImageView imageView) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            Image image = SwingFXUtils.toFXImage(bufImage, null);
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
