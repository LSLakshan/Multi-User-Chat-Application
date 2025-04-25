module com.example.autozen {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.autozen to javafx.fxml;
    exports com.example;
    opens com.example to javafx.fxml;
}