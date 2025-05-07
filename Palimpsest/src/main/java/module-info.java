module com.palimpsest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    opens com.palimpsest to javafx.fxml;
    exports com.palimpsest;
}