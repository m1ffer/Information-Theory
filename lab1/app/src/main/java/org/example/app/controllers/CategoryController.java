package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.example.app.model.CategoryModel;

public class CategoryController {
    @FXML
    private ComboBox<String> categoryComboBox;

    private CategoryModel model;

    public void setModel(CategoryModel model) {
        this.model = model;
        initModel();
    }

    @FXML
    public void initialize() {
    }

    public void initModel(){
        // Привязываем элементы списка к модели
        categoryComboBox.setItems(model.getCategories());

        // Двусторонняя привязка выбранного элемента
        categoryComboBox.valueProperty().bindBidirectional(model.selectedCategoryProperty());
        // Добавляем слушатель для логирования в консоль
        categoryComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("Выбранная категория изменилась: '" + oldValue + "' -> '" + newValue + "'");
            model.setCrypt(newValue);
        });
    }
}