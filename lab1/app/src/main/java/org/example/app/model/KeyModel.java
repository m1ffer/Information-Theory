package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.app.utils.CurrentLanguage;
import org.example.app.utils.Languages;

public class KeyModel {
    private final StringProperty key = new SimpleStringProperty("");
    private final CurrentLanguage currentLanguage;

    public KeyModel(CurrentLanguage l){
        currentLanguage = l;
    }

    public StringProperty keyValueProperty() {
        return key;
    }

    public final String getKey() {
        return key.get();
    }

    public final void setKey(String key) {
        this.key.set(key);
    }

    public final Languages getLanguage(){
        return currentLanguage.get();
    }
}