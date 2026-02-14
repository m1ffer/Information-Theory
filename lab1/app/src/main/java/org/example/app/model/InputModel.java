package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.app.utils.CurrentLanguage;

public class InputModel {
    private final StringProperty text = new SimpleStringProperty("");
    private final CurrentLanguage currentLanguage;

    public InputModel(CurrentLanguage l){
        currentLanguage = l;
    }

    public StringProperty textProperty() {
        return text;
    }

    public final String getText() {
        return text.get();
    }

    public final void setText(String text) {
        this.text.set(text);
    }
}