package org.example.app.model;

public class MainModel {
    private CategoryModel categoryModel;
    private KeyModel keyModel;
    public MainModel(){
        categoryModel = new CategoryModel();
        keyModel = new KeyModel();
    }
    public CategoryModel getCategoryModel(){
        return categoryModel;
    }
    public KeyModel getKeyModel(){
        return keyModel;
    }
}
