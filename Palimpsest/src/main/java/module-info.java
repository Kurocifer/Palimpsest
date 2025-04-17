module com.palimpsest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.palimpsest to javafx.fxml;
    exports com.palimpsest;
}