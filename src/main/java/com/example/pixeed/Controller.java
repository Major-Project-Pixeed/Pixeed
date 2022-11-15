package com.example.pixeed;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuButton;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
 @FXML
    private AnchorPane menuBarPane, imageViewPane;
    @FXML
    private JFXSlider brightness;
    @FXML
    private JFXSlider contrast;
    @FXML
    private JFXSlider hue;
    @FXML
    private JFXSlider saturation;
    @FXML
    private TextArea FIELD;
    @FXML
    private AnchorPane adjustPane, emojiPane;
    @FXML
    private JFXButton adjustButton;

       
 @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        angleOfImageViewPane = imageViewPane.getRotate();
        angleSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {

                imageViewPane.setRotate((angleSlider.getValue() * -1) + angleOfImageViewPane);
            }
        });

    }

 @FXML
    protected void handleZoom(ImageView imageView) {
        imageView.maxWidth(100);
        imageView.maxHeight(100);
        imageView.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double zoomFactor = 1.05;
                double deltaY = event.getDeltaY();
                if (deltaY < 0) {
                    zoomFactor = 0.95;
                }
                double finalZoomFactor = zoomFactor;
                imageView.setScaleX(imageView.getScaleX() * finalZoomFactor);
                imageView.setScaleY(imageView.getScaleY() * finalZoomFactor);
            }
        });
    }
    
@FXML
    private void ADDBUTTONPRESSED() {
        Text text = new Text();
        text.setText(FIELD.getText());
        text.setFill(COLORPICKER.getValue());
        text.setStyle("-fx-font: 50 arial");
        text.setStyle("-fx-background-color: white");
        text.setLayoutX(imageViewPane.getWidth() / 2);
        text.setLayoutY(imageViewPane.getHeight() / 2);
        text.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                text.setLayoutX(mouseEvent.getSceneX());
                text.setLayoutY(mouseEvent.getSceneY());
            }
        });
        imageViewPane.getChildren().add(text);
        COLORPICKER.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                text.setFill(COLORPICKER.getValue());
            }
        });
        FONTADJUST.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                text.setFont(new Font(FONTADJUST.getValue()));
            }
        });
    }
    
@FXML
    protected void brushButtonPressed() {
        adjustPane.setVisible(false);
        TEXTPANE.setVisible(false);
        textButton.setVisible(true);
        adjustButton.setVisible(true);

        if (activeImageView == null) {
            return;
        }
        specialEffectMenu.setVisible(false);
        areaSelectionMenu.setVisible(false);
        brushPane.setVisible(true);
        brushButton.setVisible(false);
        GraphicsContext g;
        Canvas canvas = null;
        canvas = new Canvas(imageViewPane.getWidth(), imageViewPane.getHeight());
        g = canvas.getGraphicsContext2D();
        g.drawImage(activeImageView.getImage(), activeImageView.getFitWidth(), activeImageView.getFitHeight());
        canvas.setOnMouseDragged(e -> {
            double size = brushSize.getValue();
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            if (eraserToggleButton.isSelected()) {
                    g.clearRect(x, y, size, size);
            } else {
                g.setFill(brushColorPicker.getValue());
                g.fillOval(x, y, size, size);
            }
        });
        imageViewPane.getChildren().add(canvas);
    }

 @FXML
    protected void SETRAIN2() throws FileNotFoundException {
        Filters filters = new Filters();
        filters.setRain2Filter(imageViewPane);
    }

    @FXML
    protected void SETRAIN3() throws FileNotFoundException {
        Filters filters = new Filters();
        filters.setRain3Filter(imageViewPane);
    }

    @FXML
    protected void SETSUNSHINE1() throws FileNotFoundException {
        Filters filters = new Filters();
        filters.setSunshine1Filter(imageViewPane);
    }

    @FXML
    protected void SETSUNSHINE2() throws FileNotFoundException {
        Filters filters = new Filters();
        filters.setSunshine2Filter(imageViewPane);
    }
    @FXML
    protected void UPSCALEIMAGE() throws IOException {
        UpScaleImage scale = new UpScaleImage();
        scale.resize(file.getAbsolutePath(), "D:\\upscaleOutput.jpg", upscaleSlider.getValue());
    }


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void pickColor() throws FileNotFoundException {

        PickColor pickColor = new PickColor(activeImageView, file);
        double ratio1 = pickColor.ratio1;
        double ratio2 = pickColor.ratio2;
        double ratio = Math.max(ratio2, ratio1);
        Mat mat = pickColor.matrix;
        final Mat[] tmpimage = {new Mat()};
        ImageView imageView = new ImageView();
        imageViewPane.getChildren().add(imageView);
        System.out.println(activeImageView.getFitHeight()+" "+activeImageView.getFitWidth());
        System.out.println(activeImageView.getImage().getHeight()+" "+activeImageView.getImage().getWidth());
        activeImageView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int x = (int) ((int) ratio * (mouseEvent.getSceneX() - activeImageView.getLayoutX()));
                int y = (int) ((int) ratio * (mouseEvent.getSceneY() - activeImageView.getLayoutY()));
                double arr[] = mat.get(x, y);
////                for (int i = 0; i < arr.length; i++)
////                    System.out.print(arr[i] + " ");
//                    Scalar scalar = new Scalar((int)arr[2],(int) arr[1],(int) arr[0]);
//                System.out.println(scalar);
                System.out.println(arr[2] + " " + arr[1] + " " + arr[0]);
                tmpimage[0] = new Mat(200, 200, mat.type(), new Scalar(arr[0], arr[1], arr[2]));
                PickColor.showResult(tmpimage[0], imageViewPane, imageView);
//                System.out.println();
            }
        });
        activeImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                RedEyeCorrection.showResult(tmpimage[0], activeImageView);
            }
        });


    }
    @FXML
    protected void rectangleSelection() throws FileNotFoundException {
        SelectArea selectArea = new SelectArea(file, activeImageView);
        selectArea.makeRectangle(imageViewPane, activeImageView, file);
    }

    @FXML
    protected void ellipseSelection() throws FileNotFoundException {
        SelectArea selectArea = new SelectArea(file, activeImageView);
        selectArea.makeEllipse(imageViewPane, activeImageView);
    }

    @FXML
    protected void lassoSelection() throws FileNotFoundException {
        SelectArea selectArea = new SelectArea(file, activeImageView);
        selectArea.makeLasso(imageViewPane, activeImageView, file);
    }

    @FXML
    protected void cropButtonPressed() throws FileNotFoundException {
        SelectArea selectArea = new SelectArea(file, activeImageView);
        selectArea.crop(file, activeImageView, imageViewPane);
    }
    @FXML
    void SETFRAME1(ActionEvent event) throws FileNotFoundException {
        ImageFrame.SETFRAME1(imageViewPane);
        stk.push(imageViewPane.getChildren());
    }

    @FXML
    void SETFRAME2(ActionEvent event) throws FileNotFoundException {
        ImageFrame.SETFRAME2(imageViewPane);
        stk.push(imageViewPane.getChildren());
    }


    @FXML
    void SETFRAME3(ActionEvent event) throws FileNotFoundException {
        ImageFrame.SETFRAME3(imageViewPane);
    }


    @FXML
    void SETFRAME4(ActionEvent event) throws FileNotFoundException {
        ImageFrame.SETFRAME4(imageViewPane);
    }


    @FXML
    void SETFRAME5(ActionEvent event) throws FileNotFoundException {
        ImageFrame.SETFRAME5(imageViewPane);
        stk.push(imageViewPane.getChildren());
    }

    @FXML
    void SETFRAME6(ActionEvent event) throws FileNotFoundException {

        ImageFrame.SETFRAME6(imageViewPane);
        stk.push(imageViewPane.getChildren());
    }

 @FXML
    private JFXSlider smoothingSlider;

    @FXML
    protected void smoothingButtonPressed() {
        smoothingSlider.setDisable(false);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        smoothingSlider.setOnMouseDragged(e -> {
            Mat image = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
            Mat dst = image.clone();
            Imgproc.bilateralFilter(image, dst, (int) smoothingSlider.getValue(), smoothingSlider.getValue() * 2, smoothingSlider.getValue() / 2);
            RedEyeCorrection.showResult(dst, activeImageView);
        });
    }


    @FXML
    protected void COMPRESS() throws IOException {
        Compress compress = new Compress();
        Image image = activeImageView.getImage();
        System.out.println(compressQuality.getValue() / 100);
        compress.compressImage(image, compressQuality.getValue() / 100);
    }

}