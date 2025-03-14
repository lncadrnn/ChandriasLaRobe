module com.example.chandriaslarobe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chandriaslarobe to javafx.fxml;
    exports com.example.chandriaslarobe;
}