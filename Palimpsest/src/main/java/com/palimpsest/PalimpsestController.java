package com.palimpsest;

import com.palimpsest.model.TextCopy;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.logging.Logger;

public class PalimpsestController {
    private static final Logger LOGGER = Logger.getLogger(PalimpsestController.class.getName());
    private final TextCopy dummyCopy = new TextCopy("test", "12:00 PM");

    @FXML private Label text1;
    @FXML private Label time1;
    @FXML private Button copy1;

    @FXML
    public void initialize() {
        text1.setText(dummyCopy.getText());
        time1.setText(dummyCopy.getTimestamp());
        copy1.setOnAction(event -> {
            LOGGER.info("Copy1 button clicked - copying: text=" + dummyCopy.getText());
            dummyCopy.copyToClipboard();
            LOGGER.info("Copied to clipboard: " + dummyCopy.getText());
        });
    }

    @FXML
    public void copy1() {
        LOGGER.info("Other copy button clicked - placeholder");
    }

    @FXML
    public void copy() {
        LOGGER.info("Other copy button clicked - placeholder");
    }
}