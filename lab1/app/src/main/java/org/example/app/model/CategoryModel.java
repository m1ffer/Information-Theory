package org.example.app.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryModel {
    // Список категорий (наблюдаемый)
    private final ObservableList<String> categories = FXCollections.observableArrayList(
            "Шифр Плейфейра", "Алгоритм Виженера, прогрессивный ключ"
    );

    // Выбранная категория (наблюдаемое свойство)
    private final ObjectProperty<String> selectedCategory = new SimpleObjectProperty<>();

    public ObservableList<String> getCategories() {
        return categories;
    }

    public ObjectProperty<String> selectedCategoryProperty() {
        return selectedCategory;
    }

    public final String getSelectedCategory() {
        return selectedCategory.get();
    }

    public final void setSelectedCategory(String category) {
        selectedCategory.set(category);
    }

    // Метод для добавления новой категории (если нужно)
    public void addCategory(String category) {
        categories.add(category);
    }
}
