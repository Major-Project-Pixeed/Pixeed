module com.example.pixeed
{
    requires javafx.controls;
    requires javafx.fxml;
    opens com.example.pixeed to javafx.fxml;
    exports com.example.pixeed;
}