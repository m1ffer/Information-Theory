package org.example.app.controllers;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.example.app.model.MainModel;

public class MainController {
    private MainModel model;
    @FXML
    private CategoryController categoryController;
    @FXML
    private KeyController keyController;
    public void setModel(MainModel model){
        this.model = model;
        categoryController.setModel(model.getCategoryModel());
        keyController.setModel(model.getKeyModel());
    }
    @FXML
    public void initialize() {
        // Здесь ничего не делаем, т.к. модель ещё не передана.
        // Но можно проверить, что categoryPanelController не null.
        System.out.println("CategoryController initialized");
    }
    @FXML
    private void handleShowSelection() {
        if (model != null) {
            String selected = model.getCategoryModel().getSelectedCategory();
            System.out.println("aaa");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Выбор");
            alert.setHeaderText("Выбранная категория");
            alert.setContentText(selected == null ? "Ничего не выбрано" : selected);
            alert.showAndWait();
        }
    }
}
