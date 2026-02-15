package org.example.app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.app.crypts.CryptUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CryptModel {

    private final StringProperty crypt = new SimpleStringProperty("");
    private final StringProperty input = new SimpleStringProperty("");
    private final StringProperty output = new SimpleStringProperty("");
    private final StringProperty key = new SimpleStringProperty("");
    private final StringProperty sourceFile = new SimpleStringProperty("");
    private final StringProperty resultFile = new SimpleStringProperty("");

    // --- Свойства ---

    public StringProperty inputProperty() {
        return input;
    }

    public StringProperty cryptProperty(){
        return crypt;
    }

    public StringProperty outputProperty() {
        return output;
    }

    public StringProperty keyProperty() {
        return key;
    }

    public StringProperty sourceFileProperty(){
        return sourceFile;
    }

    public StringProperty resultFileProperty(){
        return resultFile;
    }

    public void encrypt() {
        try {
            String src = getSource();
            if (crypt.get() == null || crypt.get().isEmpty()){
                throw new IllegalArgumentException ("Сначала выберите шифр");
            }
            if (crypt.get().contains("Плейфейр")) {
                output.set(CryptUtil.playfairEncrypt(key.get(), src));
            }
            else if (crypt.get().contains("Виженер")){
                output.set(CryptUtil.vigenereDecrypt(key.get(), src));
            }
            writeToFile();
        }
        catch(IllegalArgumentException e){
            output.set(e.getMessage());
        }
        catch(Exception e){
            output.set("Ошибка");
        }
    }

    public void decrypt() {
        try{
            String src = getSource();
            if (crypt.get() == null || crypt.get().isEmpty())
                throw new IllegalArgumentException("Сначала выберите шифр");
            if(crypt.get().contains("Плейфейр")) {
                output.set(CryptUtil.playfairDecrypt(key.get(), src));
            }
            else if (crypt.get().contains("Виженер")){
                output.set(CryptUtil.vigenereEncrypt(key. get(), src));
            }
            writeToFile();
        }
        catch(IllegalArgumentException e){
            output.set(e.getMessage());
        }
        catch(Exception e){
            output.set("Ошибка");
        }
    }

    public String getSource() throws IOException {
        if (sourceFile.get().isEmpty())
            return input.get();
        return Files.readString(Path.of(sourceFile.get()));
    }

    public void writeToFile() throws IOException{
        if (!resultFile.get().isEmpty()){
            Path path = Path.of(resultFile.get());
            if (Files.exists(path) && Files.isRegularFile(path)){
                Files.writeString(path, output.get(), StandardOpenOption.TRUNCATE_EXISTING);
                return;
            }
            resultFile.set("");
        }
    }

    public void clear() {
        output.set("");
        key.set("");
        input.set("");
        resultFile.set("");
        sourceFile.set("");
    }
}