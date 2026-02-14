package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KeyModel {
    private final StringProperty key = new SimpleStringProperty("");

    public StringProperty keyValueProperty() {
        return key;
    }

    public final String getKey() {
        return key.get();
    }

    public final void setKey(String key) {
        this.key.set(key);
    }
}