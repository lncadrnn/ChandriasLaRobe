module com.example.chandriaslarobe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.example.chandriaslarobe to javafx.fxml;
    exports com.example.chandriaslarobe;
}