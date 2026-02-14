package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.app.model.OutputModel;

public class OutputController {

    @FXML
    private Label outputLabel;

    private OutputModel model;

    public void setModel(OutputModel model) {
        this.model = model;
        initModel();
    }

    @FXML
    public void initialize() {
        // Привязка текста метки к свойству модели
        // (будет выполнена после установки модели)
    }

    // Лучше вызывать этот метод после setModel
    public void initModel() {
        outputLabel.textProperty().bind(model.textProperty());
    }
}