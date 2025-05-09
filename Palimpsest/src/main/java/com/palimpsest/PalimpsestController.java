package com.palimpsest;

import com.palimpsest.model.TextCopy;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PalimpsestController {
    private static final Logger LOGGER = Logger.getLogger(PalimpsestController.class.getName());
    private final List<TextCopy> textCopies = new ArrayList<>(10);

    @FXML private Label text1;
    @FXML private Label time1;
    @FXML private Button copy1;
    @FXML private Label text2;
    @FXML private Label time2;
    @FXML private Button copy2;
    @FXML private Label text3;
    @FXML private Label time3;
    @FXML private Button copy3;
    @FXML private Label text4;
    @FXML private Label time4;
    @FXML private Button copy4;
    @FXML private Label text5;
    @FXML private Label time5;
    @FXML private Button copy5;
    @FXML private Label text6;
    @FXML private Label time6;
    @FXML private Button copy6;
    @FXML private Label text7;
    @FXML private Label time7;
    @FXML private Button copy7;
    @FXML private Label text8;
    @FXML private Label time8;
    @FXML private Button copy8;
    @FXML private Label text9;
    @FXML private Label time9;
    @FXML private Button copy9;
    @FXML private Label text10;
    @FXML private Label time10;
    @FXML private Button copy10;

    @FXML
    public void initialize() {
        // Populate list with 10 dummies (newest first)
        for (int i = 10; i >= 1; i--) {
            addTextCopy(new TextCopy("copy" + i, String.format("12:%02d PM", i)));
        }

        // Map list to UI rows
        Label[] textLabels = {text1, text2, text3, text4, text5, text6, text7, text8, text9, text10};
        Label[] timeLabels = {time1, time2, time3, time4, time5, time6, time7, time8, time9, time10};
        Button[] copyButtons = {copy1, copy2, copy3, copy4, copy5, copy6, copy7, copy8, copy9, copy10};

        for (int i = 0; i < 10; i++) {
            final int index = i;
            if (i < textCopies.size()) {
                TextCopy copy = textCopies.get(i);
                textLabels[i].setText(copy.getText());
                timeLabels[i].setText(copy.getTimestamp());
                copyButtons[i].setOnAction(event -> {
                    LOGGER.info("Copy button " + (index + 1) + " clicked - copying: text=" + copy.getText());
                    copy.copyToClipboard();
                    LOGGER.info("Copied to clipboard: " + copy.getText());
                });
            } else {
                textLabels[i].setText("");
                timeLabels[i].setText("");
                copyButtons[i].setOnAction(null);
            }
        }
    }

    public void addTextCopy(TextCopy textCopy) {
        textCopies.add(0, textCopy);
        if (textCopies.size() > 10) {
            textCopies.remove(10);
        }
    }

    @FXML
    public void copy() {
        LOGGER.info("Other copy button clicked - placeholder");
    }
}