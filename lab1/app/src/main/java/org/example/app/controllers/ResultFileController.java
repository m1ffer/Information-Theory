package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.app.model.ResultFileModel;

import java.io.File;

public class ResultFileController {

    @FXML
    private Button fileButton;  // соответствует fx:id в FXML
    @FXML
    private Label filePath;

    private ResultFileModel model;

    public void setModel(ResultFileModel model) {
        this.model = model;
        filePath.textProperty().bind(model.filePathProperty());
    }

    @FXML
    private void handleFileChoice() {
        // Получаем текущее окно (Stage) из кнопки
        Stage stage = (Stage) fileButton.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null && model != null) {
            model.setFilePath(selectedFile.getAbsolutePath());
        }
    }
}