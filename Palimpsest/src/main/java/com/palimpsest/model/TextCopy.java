package com.palimpsest.model;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class TextCopy {
    private final String text;
    private final String timestamp;

    public TextCopy(String text, String timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    @Override
    public String toString() {
        return text;
    }
}