module com.example.pixeed
{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing;
    requires opencv;
    requires com.jfoenix;
    exports com.example.pixeed;
    opens com.example.pixeed to javafx.fxml;
}