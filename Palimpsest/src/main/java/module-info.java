module com.example.palimpsest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.palimpsest to javafx.fxml;
    exports com.example.palimpsest;
}