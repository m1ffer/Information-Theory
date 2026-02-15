package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResultFileModel {
    private final StringProperty filePath = new SimpleStringProperty("");

    public StringProperty filePathProperty() {
        return filePath;
    }

    public final String getFilePath() {
        return filePath.get();
    }

    public final void setFilePath(String path) {
        filePath.set(path);
        System.out.println("В качестве итогового файла установлен: " + path);
    }
}