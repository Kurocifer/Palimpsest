package com.palimpsest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class TextCopy {
    private String text;
    private String timestamp;

    @JsonCreator
    public TextCopy(@JsonProperty("text") String text, @JsonProperty("timestamp") String timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    @Override
    public String toString() {
        return text + " - " + timestamp;
    }
}