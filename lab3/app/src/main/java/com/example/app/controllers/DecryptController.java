package com.example.app.controllers;

import com.example.app.alert.AlertUtil;
import com.example.app.models.DecryptModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class DecryptController {
    private final DecryptModel model;

    @FXML
    public TextField pField;
    @FXML
    public TextField xField;
    @FXML
    public Button inputFileButton;
    @FXML
    public Label inputFileLabel;
    @FXML
    public Button outputFileButton;
    @FXML
    public Label outputFileLabel;
    @FXML
    public Button decryptButton;
    @FXML
    public TextArea resultArea;

    @FXML
    public void initialize(){
        pField.textProperty().bindBidirectional(model.getPProperty());
        xField.textProperty().bindBidirectional(model.getXProperty());
        inputFileLabel.textProperty().bindBidirectional(model.getInputFileProperty());
        outputFileLabel.textProperty().bindBidirectional(model.getOutputFileProperty());
        resultArea.textProperty().bindBidirectional(model.getResultProperty());
        resultArea.setEditable(false);
        pField.setTextFormatter(EncryptController.ONLY_DIGITS());
        xField.setTextFormatter(EncryptController.ONLY_DIGITS());
        pField.textProperty().addListener((ignored) -> {
            model.resetResult();
        });
        xField.textProperty().addListener((ignored) -> {
            model.resetResult();
        });
    }

    public void chooseInputFile(ActionEvent actionEvent) {
        Path path = EncryptController.chooseOpenFile(inputFileButton.getScene().getWindow());
        if (path != null)
            model.setInputFile(path);
    }

    public void chooseOutputFile(ActionEvent actionEvent) {
        Path path = EncryptController.chooseSaveFile(outputFileButton.getScene().getWindow());
        if (path != null)
            model.setOutputFile(path);
    }

    public void decrypt(ActionEvent actionEvent) {
        try{
            model.decrypt();
        }
        catch(Exception e){
            AlertUtil.showError(e.getMessage());
        }
    }
}
