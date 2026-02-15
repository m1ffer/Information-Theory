package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResultFileModel {
    private final StringProperty filePath = new SimpleStringProperty("");
    private final StringProperty output = new SimpleStringProperty("");

    public StringProperty filePathProperty() {
        return filePath;
    }

    public StringProperty outputProperty(){
        return output;
    }

    public final void setFilePath(String path) {
        filePath.set(path);
        output.set("");
        System.out.println("В качестве итогового файла установлен: " + path);
    }
}