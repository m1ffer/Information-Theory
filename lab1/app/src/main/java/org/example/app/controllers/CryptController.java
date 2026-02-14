package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.app.model.CryptModel;

public class CryptController {

    @FXML
    private Button encryptButton;
    @FXML
    private Button decryptButton;
    @FXML
    private Button clearButton;

    private CryptModel model;

    // Сеттер для внедрения модели (вызывается извне)
    public void setModel(CryptModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        // Здесь можно добавить слушатели или привязки, если потребуется.
        // Например, можно отключать кнопки, если нет данных, но пока оставим.
    }

    @FXML
    private void handleEncrypt() {
        model.encrypt();
    }

    @FXML
    private void handleDecrypt() {
        model.decrypt();
    }

    @FXML
    private void handleClear() {
        model.clear();
    }
}