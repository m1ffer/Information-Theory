package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.app.model.SourceFileModel;

import java.io.File;

public class SourceFileController {
    @FXML
    private Button chooseFileButton;  // fx:id кнопки должен быть "chooseFileButton"
    @FXML
    private Label filePathLabel;

    private SourceFileModel model;

    // Внедрение модели (вызывается из родительского контроллера или MainApplication)
    public void setModel(SourceFileModel model) {
        this.model = model;
        filePathLabel.textProperty().bind(model.filePathProperty());
    }

    @FXML
    public void initialize() {
        // Устанавливаем обработчик нажатия кнопки
        chooseFileButton.setOnAction(event -> handleChooseFile());
    }

    private void handleChooseFile() {
        // Получаем окно-владелец из сцены кнопки
        Stage ownerStage = (Stage) chooseFileButton.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");

        // Можно добавить фильтры расширений, например:
        // fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(ownerStage);

        if (selectedFile != null && model != null) {
            model.setFilePath(selectedFile.getAbsolutePath());
        }
    }
}