package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.app.crypts.CryptUtil;

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
        if(crypt.get() != null && crypt.get().contains("Плейфейр")) {
            output.set(CryptUtil.playfairEncrypt(key.get(), input.get()));
        }
    }

    public void decrypt() {
        // Логика расшифровки
        if(crypt.get() != null && crypt.get().contains("Плейфейр")) {
            output.set(CryptUtil.playfairDecrypt(key.get(), input.get()));
        }
    }

    public void clear() {

    }
}