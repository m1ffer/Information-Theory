package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.app.utils.CurrentLanguage;

public class InputModel {
    private final StringProperty text = new SimpleStringProperty("");
    private final StringProperty output = new SimpleStringProperty("");
    private final CurrentLanguage currentLanguage;

    public InputModel(CurrentLanguage l){
        currentLanguage = l;
    }

    public StringProperty textProperty() {
        return text;
    }

    public StringProperty outputProperty(){
        return output;
    }

    public void resetOutput(){
        output.set("");
    }
}