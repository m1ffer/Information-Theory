package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.app.model.KeyModel;

public class KeyController {

    @FXML
    private TextField keyTextField;  // соответствует fx:id="keyTextField" в FXML

    @FXML
    public void initialize(){

    }

    private KeyModel model;

    // Сеттер для внедрения модели
    public void setModel(KeyModel model) {
        this.model = model;
        initModel();
    }

    // Метод инициализации после установки модели
    public void initModel() {
        if (model != null) {
            // Двусторонняя привязка текста поля к свойству модели
            keyTextField.textProperty().bindBidirectional(model.keyValueProperty());
        }
    }
}