package com.palimpsest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palimpsest.model.TextCopy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.util.Duration;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PalimpsestController {
    private static final Logger LOGGER = Logger.getLogger(PalimpsestController.class.getName());
    private final List<TextCopy> textCopies = new ArrayList<>(10);
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    private String lastClipboardText = "";
    private static final String HISTORY_FILE = Paths.get(System.getProperty("user.home"), ".palimpsest", "history.json").toString();
    private final ObjectMapper mapper = new ObjectMapper();

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
        // Load history from file
        loadHistory();

        // Initialize UI
        updateUI();

        // Start clipboard monitoring
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> checkClipboard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void checkClipboard() {
        if (!PalimpsestMain.getTrayManager().getIsPaused()) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            String currentText = clipboard.getString();
            if (currentText != null && !currentText.isEmpty() && !currentText.equals(lastClipboardText)) {
                lastClipboardText = currentText;
                String timestamp = LocalTime.now().format(timeFormatter);
                TextCopy newCopy = new TextCopy(currentText, timestamp);
                addTextCopy(newCopy);
                LOGGER.info("New clipboard text: " + currentText + ", timestamp: " + timestamp);
                Platform.runLater(this::updateUI);
            }
        }
    }

    private void updateUI() {
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
        saveHistory();
    }

    @FXML
    public void copy() {
        LOGGER.info("Other copy button clicked - placeholder");
    }

    private void saveHistory() {
        try {
            File historyDir = new File(HISTORY_FILE).getParentFile();
            if (!historyDir.exists()) {
                historyDir.mkdirs();
            }
            mapper.writeValue(new File(HISTORY_FILE), textCopies);
            LOGGER.info("Saved clipboard history to " + HISTORY_FILE);
        } catch (Exception e) {
            LOGGER.severe("Failed to save clipboard history: " + e.getMessage());
        }
    }

    private void loadHistory() {
        try {
            File historyFile = new File(HISTORY_FILE);
            if (historyFile.exists()) {
                List<TextCopy> loadedCopies = mapper.readValue(historyFile, new TypeReference<List<TextCopy>>() {});
                textCopies.clear();
                textCopies.addAll(loadedCopies);
                if (textCopies.size() > 10) {
                    textCopies.subList(10, textCopies.size()).clear();
                }
                LOGGER.info("Loaded " + textCopies.size() + " clipboard entries from " + HISTORY_FILE);
            } else {
                LOGGER.info("No clipboard history file found at " + HISTORY_FILE);
            }
        } catch (Exception e) {
            if (e.getCause() != null) {
                LOGGER.severe("Failed to load clipboard history: " + e.getMessage() + "; Cause: " + e.getCause().getMessage());
            } else {
                LOGGER.severe("Failed to load clipboard history: " + e.getMessage() + "; Cause: " + "None");
            }
        }
    }
}