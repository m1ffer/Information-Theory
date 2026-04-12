package com.example.app.controllers;

import com.example.app.alert.AlertUtil;
import com.example.app.models.EncryptModel;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class EncryptController {
    private static final String NO_FILE = "Файл не выбран";
    private static final String NO_Y = "Здесь будет публичный ключ";
    static TextFormatter<String> ONLY_DIGITS() {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 7 && newText.matches("\\d*"))
                return change;
            return null;
        });
    }
    public static Path chooseOpenFile(Window owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");

        File file = fileChooser.showOpenDialog(owner);

        return file != null ? file.toPath() : null;
    }
    public static Path chooseSaveFile(Window owner) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Сохранить файл");

        fileChooser.setInitialFileName("output.enc");

        File file = fileChooser.showSaveDialog(owner);

        return file != null ? file.toPath() : null;
    }
    private final EncryptModel model;
    @FXML
    public TextField pField;
    @FXML
    public Button computeGButton;
    @FXML
    public ComboBox<Integer> gComboBox;
    @FXML
    public TextField xField;
    @FXML
    public Label yLabel;
    @FXML
    public TextField kField;
    @FXML
    public Button inputFileButton;
    @FXML
    public Label inputFileLabel;
    @FXML
    public Button outputFileButton;
    @FXML
    public Label outputFileLabel;
    @FXML
    public Button encryptButton;
    @FXML
    public TextArea resultArea;

    @FXML
    public void initialize(){
        pField.textProperty().bindBidirectional(model.getPProperty());
        xField.textProperty().bindBidirectional(model.getXProperty());
        yLabel.textProperty().bindBidirectional(model.getYProperty());
        kField.textProperty().bindBidirectional(model.getKProperty());
        inputFileLabel.textProperty().bindBidirectional(model.getInputFileProperty());
        outputFileLabel.textProperty().bindBidirectional(model.getOutputFileProperty());
        resultArea.textProperty().bindBidirectional(model.getResultProperty());
        resultArea.setEditable(false);
        gComboBox.setItems(model.getGList());
        gComboBox.setButtonCell(new ListCell<Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(gComboBox.getPromptText());
                } else {
                    setText(String.valueOf(item));
                }
            }
        });

        pField.setTextFormatter(ONLY_DIGITS());
        xField.setTextFormatter(ONLY_DIGITS());
        kField.setTextFormatter(ONLY_DIGITS());

        bindDisable();

        pField.textProperty().addListener((obs, oldVal, newVal) -> {
            model.resetG();
        });
        xField.textProperty().addListener((obs, oldVal, newVal) -> model.resetY());
        yLabel.textProperty().set(NO_Y);
        yLabel.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty())
                yLabel.textProperty().set(NO_Y);
        });
    }

    private void bindDisable(){
        BooleanBinding notReady = model.getGReady().not();

        gComboBox.disableProperty().bind(notReady);
        xField.disableProperty().bind(notReady);
        kField.disableProperty().bind(notReady);
        inputFileButton.disableProperty().bind(notReady);
        outputFileButton.disableProperty().bind(notReady);
        encryptButton.disableProperty().bind(notReady);
    }

    public void computeG(ActionEvent actionEvent) {
        try{
            model.computeG();
            Platform.runLater(() -> gComboBox.setValue(null));
        }
        catch(Exception e){
            AlertUtil.showError(e.getMessage());
        }
    }

    public void chooseOutputFile(ActionEvent actionEvent) {
        Path path = chooseSaveFile(outputFileButton.getScene().getWindow());
        if (path != null)
            model.setOutputFile(path);
    }

    public void chooseInputFile(ActionEvent actionEvent) {
        Path path = chooseOpenFile(inputFileButton.getScene().getWindow());
        if (path != null)
            model.setInputFile(path);
    }

    public void encrypt(ActionEvent actionEvent) {
        try{
            if (gComboBox.getValue() == null)
                AlertUtil.showError("Сначала выберите g");
            else
                model.encrypt(gComboBox.getValue());
        }
        catch(Exception e){
            AlertUtil.showError(e.getMessage());
        }
    }
}
