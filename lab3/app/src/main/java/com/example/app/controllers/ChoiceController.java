package com.example.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ChoiceController {
    private final ApplicationContext context;

    @FXML
    private StackPane contentPane;

    private Node encryptView;
    private Node decryptView;

    @FXML
    public void initialize() {
        try {
            encryptView = loadView("/com/example/app/encrypt-window.fxml");
            decryptView = loadView("/com/example/app/decrypt-window.fxml");

            // по умолчанию показываем шифрование
            contentPane.getChildren().setAll(encryptView);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load views", e);
        }
    }

    @FXML
    public void onEncryptClicked() {
        contentPane.getChildren().setAll(encryptView);
    }

    @FXML
    public void onDecryptClicked() {
        contentPane.getChildren().setAll(decryptView);
    }

    // =========================
    // Загрузка FXML через Spring
    // =========================

    private Node loadView(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

        // ВАЖНО: связываем со Spring
        loader.setControllerFactory(context::getBean);

        return loader.load();
    }
}
