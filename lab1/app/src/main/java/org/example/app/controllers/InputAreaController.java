package org.example.app.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.example.app.model.InputModel;

public class InputAreaController {

    @FXML
    private TextArea inputArea; // fx:id должно совпадать

    private InputModel model;

    // Сеттер для внедрения модели (будет вызван извне)
    public void setModel(InputModel model) {
        this.model = model;
        initModel();
    }

    // Метод для инициализации привязок после установки модели
    public void initModel() {
        inputArea.textProperty().bindBidirectional(model.textProperty());
        inputArea.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                model.resetOutput();
                if (!newValue.equals(""))
                    model.resetSourceFile();
            }
        });
    }
}