package com.palimpsest;

import com.palimpsest.model.TextCopy;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.logging.Logger;

public class PalimpsestController {
    private static final Logger LOGGER = Logger.getLogger(PalimpsestController.class.getName());
    private final TextCopy dummyCopy = new TextCopy("test", "12:00 PM");

    @FXML
    private Label text1;
    @FXML
    private Label time1;

    @FXML
    public void copy() {
        LOGGER.info("Copy button clicked - testing dummy TextCopy: text=" + dummyCopy.getText() + ", timestamp=" + dummyCopy.getTimestamp());
        dummyCopy.copyToClipboard();
        LOGGER.info("Dummy text copied to clipboard: " + dummyCopy.getText());
    }

    @FXML
    public void copy1() {
        TextCopy text1Copy = new TextCopy(text1.getText(), time1.getText());

        text1Copy.copyToClipboard();
        LOGGER.info("Copy button clicked - testing dummy TextCopy: text=" + dummyCopy.getText() + ", timestamp=" + dummyCopy.getTimestamp());
        LOGGER.info("Dummy text copied to clipboard: " + dummyCopy.getText());
    }
}