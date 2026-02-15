package org.example.app.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.app.utils.CurrentLanguage;
import org.example.app.utils.Languages;
import java.util.HashMap;

public class CategoryModel {
    // Список категорий (наблюдаемый)
    private final ObservableList<String> categories = FXCollections.observableArrayList();
    private final HashMap<String, Languages> languages = new HashMap<>(categories.size());
    private final CurrentLanguage currentLanguage;
    private final StringProperty textProperty = new SimpleStringProperty();
    private CryptModel clearModel;

    public CategoryModel(CurrentLanguage l){
        currentLanguage = l;
        String s = "Шифр Плейфейра";
        categories.add(s);
        languages.put(s, Languages.ENGLISH);
        s = "Шифр Виженера, прогрессивный ключ";
        categories.add(s);
        languages.put(s, Languages.RUSSIAN);
    }

    // Выбранная категория (наблюдаемое свойство)
    private final ObjectProperty<String> selectedCategory = new SimpleObjectProperty<>();

    public ObservableList<String> getCategories() {
        return categories;
    }

    public ObjectProperty<String> selectedCategoryProperty() {
        return selectedCategory;
    }

    public StringProperty textProperty(){
        return textProperty;
    }

    public final String getSelectedCategory() {
        return selectedCategory.get();
    }

    public void setLanguage(Languages l){
        currentLanguage.set(l);
    }

    public Languages getLanguage(){
        return currentLanguage.get();
    }

    public void setLanguageByChoice(String choice){
        setLanguage(languages.get(choice));
    }

    public final void setSelectedCategory(String category) {
        selectedCategory.set(category);
    }

    // Метод для добавления новой категории (если нужно)
    public void addCategory(String category) {
        categories.add(category);
    }

    public void setCrypt(String crypt){
        textProperty.set(crypt);
        currentLanguage.set(languages.get(crypt));
        clearAll();
    }

    public void setClearModel(CryptModel model){
        clearModel = model;
    }

    public void clearAll(){
        clearModel.clear();
    }
}
