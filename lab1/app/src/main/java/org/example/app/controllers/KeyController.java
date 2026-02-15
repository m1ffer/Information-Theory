package org.example.app.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.example.app.model.KeyModel;
import org.example.app.utils.Languages;

import java.util.function.UnaryOperator;

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
        keyTextField.textProperty().bindBidirectional(model.keyValueProperty());
        // Создаём фильтр, разрешающий только не цифры
        keyTextField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                model.clearOutput();
            }
        });
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            return switch (model.getLanguage()){
                case Languages.RUSSIAN ->{
                    if (newText.matches("[а-яё]*"))
                        yield change;
                    yield null;
                }
                case Languages.ENGLISH ->{
                    if (newText.matches("[a-z]*"))
                        yield change;
                    yield null;
                }
                case Languages.NUMS ->{
                    throw new RuntimeException("Ну как так");
                }
            };
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        keyTextField.setTextFormatter(textFormatter);
    }
}