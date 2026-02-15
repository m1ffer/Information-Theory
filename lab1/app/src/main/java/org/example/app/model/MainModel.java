package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import org.example.app.utils.CurrentLanguage;
import org.example.app.utils.Languages;
import javafx.beans.property.StringProperty;

public class MainModel {
    private final CategoryModel categoryModel;
    private final KeyModel keyModel;
    private final SourceFileModel sourceFileModel;
    private final ResultFileModel resultFileModel;
    private final InputModel inputModel;
    private final OutputModel outputModel;
    private final CryptModel cryptModel;

    private final CurrentLanguage currentLanguage;

    private final StringProperty crypt = new SimpleStringProperty("");
    private final StringProperty input = new SimpleStringProperty("");
    private final StringProperty key = new SimpleStringProperty("");
    private final StringProperty output = new SimpleStringProperty("");
    private final StringProperty sourceFile = new SimpleStringProperty("");
    private final StringProperty resultFile = new SimpleStringProperty("");

    public MainModel(){
        currentLanguage = new CurrentLanguage();

        keyModel = new KeyModel(currentLanguage);
        keyModel.keyValueProperty().bindBidirectional(key);
        keyModel.outputProperty().bindBidirectional(output);

        sourceFileModel = new SourceFileModel();
        sourceFileModel.filePathProperty().bindBidirectional(sourceFile);
        sourceFileModel.inputProperty().bindBidirectional(input);

        resultFileModel = new ResultFileModel();
        resultFileModel.filePathProperty().bindBidirectional(resultFile);

        inputModel = new InputModel(currentLanguage);
        inputModel.textProperty().bindBidirectional(input);
        inputModel.outputProperty().bindBidirectional(output);
        inputModel.sourceFileProperty().bindBidirectional(sourceFile);

        outputModel = new OutputModel();
        outputModel.textProperty().bindBidirectional(output);

        cryptModel = new CryptModel();
        cryptModel.inputProperty().bindBidirectional(input);
        cryptModel.keyProperty().bindBidirectional(key);
        cryptModel.outputProperty().bindBidirectional(output);
        cryptModel.cryptProperty().bindBidirectional(crypt);
        cryptModel.sourceFileProperty().bindBidirectional(sourceFile);
        cryptModel.resultFileProperty().bindBidirectional(resultFile);

        categoryModel = new CategoryModel(currentLanguage);
        categoryModel.setClearModel(cryptModel);
        categoryModel.textProperty().bindBidirectional(crypt);
    }
    public CategoryModel getCategoryModel(){
        return categoryModel;
    }
    public KeyModel getKeyModel(){
        return keyModel;
    }
    public SourceFileModel getSourceFileModel(){
        return sourceFileModel;
    }
    public InputModel getInputModel(){
        return inputModel;
    }

    public OutputModel getOutputModel(){
        return outputModel;
    }

    public CryptModel getCryptModel(){
        return cryptModel;
    }

    public ResultFileModel getResultFileModel(){
        return resultFileModel;
    }
}
