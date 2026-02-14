package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CryptModel {

    private final StringProperty crypt = new SimpleStringProperty("");
    private final StringProperty input = new SimpleStringProperty("");
    private final StringProperty output = new SimpleStringProperty("");
    private final StringProperty key = new SimpleStringProperty("");

    // --- Свойства ---

    public StringProperty inputProperty() {
        return input;
    }

    public StringProperty cryptProperty(){
        return crypt;
    }

    public StringProperty outputProperty() {
        return output;
    }

    public StringProperty keyProperty() {
        return key;
    }

    // --- Бизнес-методы (заглушки) ---

    public void encrypt() {
        // Здесь будет логика шифрования: взять input и key, установить output
        // Например, просто заглушка:
        output.set(input.get() + key.get());
    }

    public void decrypt() {
        // Логика расшифровки

    }

    public void clear() {

    }
}