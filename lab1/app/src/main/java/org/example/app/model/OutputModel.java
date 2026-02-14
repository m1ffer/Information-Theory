package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OutputModel {
    private final StringProperty text = new SimpleStringProperty("");

    public StringProperty textProperty() {
        return text;
    }

    public final String getText() {
        return text.get();
    }

    public final void setText(String text) {
        this.text.set(text);
    }

    public void append(String line) {
        if (line == null) return;
        String current = text.get();
        if (current.isEmpty()) {
            text.set(line);
        } else {
            text.set(current + "\n" + line);
        }
    }

    public void clear() {
        text.set("");
    }
}