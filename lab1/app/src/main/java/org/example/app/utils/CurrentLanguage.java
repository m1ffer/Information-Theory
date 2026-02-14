package org.example.app.utils;

import javafx.scene.control.TextFormatter;

public class CurrentLanguage {
    private Languages currentLanguage;
    public Languages get(){
        return currentLanguage;
    }
    public void set(Languages l){
        currentLanguage = l;
        System.out.println("Текущий язык - " + currentLanguage.toString());
    }
}
