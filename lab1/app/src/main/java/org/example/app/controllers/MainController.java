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
    @FXML
    private InputAreaController inputAreaController;
    @FXML
    private OutputController outputController;
    @FXML
    private CryptController cryptController;
    public void setModel(MainModel model){
        this.model = model;
        categoryController.setModel(model.getCategoryModel());
        keyController.setModel(model.getKeyModel());
        inputAreaController.setModel(model.getInputModel());
        outputController.setModel(model.getOutputModel());
        cryptController.setModel(model.getCryptModel());
    }
    @FXML
    public void initialize() {}
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
