module com.example.chandriaslarobe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.chandriaslarobe to javafx.fxml;
    exports com.example.chandriaslarobe;
}