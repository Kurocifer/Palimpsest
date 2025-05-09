module com.palimpsest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires java.datatransfer;

    opens com.palimpsest to javafx.fxml;
    opens com.palimpsest.service to java.desktop;
    opens com.palimpsest.model to javafx.base;
    exports com.palimpsest;
    exports com.palimpsest.model;
}