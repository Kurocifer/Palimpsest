package com.palimpsest;

import com.palimpsest.model.TextCopy;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PalimpsestController {
    private static final Logger LOGGER = Logger.getLogger(PalimpsestController.class.getName());
    private final List<TextCopy> textCopies = new ArrayList<>(10);
    private final TextCopy dummyCopy = new TextCopy("test", "12:00 PM");

    @FXML private Label text1;
    @FXML private Label time1;

    @FXML
    public void initialize() {
        // Set row 1 from dummy (Issue #12)
        text1.setText(dummyCopy.getText());
        time1.setText(dummyCopy.getTimestamp());

        // Test list with 3 dummies
        addTextCopy(new TextCopy("copy3", "12:03 PM"));
        addTextCopy(new TextCopy("copy2", "12:02 PM"));
        addTextCopy(new TextCopy("copy1", "12:01 PM"));

        // Log list to verify
        for (int i = 0; i < textCopies.size(); i++) {
            TextCopy copy = textCopies.get(i);
            LOGGER.info("List index " + i + ": text=" + copy.getText() + ", timestamp=" + copy.getTimestamp());
        }
    }

    public void addTextCopy(TextCopy textCopy) {
        textCopies.add(0, textCopy);
        if (textCopies.size() > 10) {
            textCopies.remove(10);
        }
    }

    @FXML
    public void copy1() {
        LOGGER.info("Copy1 button clicked - copying: text=" + dummyCopy.getText());
        dummyCopy.copyToClipboard();
        LOGGER.info("Copied to clipboard: " + dummyCopy.getText());
    }

    @FXML
    public void copy() {
        LOGGER.info("Other copy button clicked - placeholder");
    }
}