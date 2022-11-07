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
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}