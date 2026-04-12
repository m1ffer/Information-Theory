package com.example.app.models;

import crypto.io.IOService;
import crypto.math.MathUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

@Service
public class DecryptModel {
    @Getter
    private final StringProperty pProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty xProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty inputFileProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty outputFileProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty resultProperty = new SimpleStringProperty("");

    private int p;
    private int x;
    private Path inputFile;
    private Path outputFile;

    public void setInputFile(Path path){
        inputFile = path;
        inputFileProperty.set(path.toString());
        resetResult();
    }
    public void resetResult(){
        resultProperty.set("");
    }
    public void setOutputFile(Path path){
        outputFile = path;
        outputFileProperty.set(path.toString());
    }

    public void decrypt() throws IOException {
        if (pProperty.get().isEmpty())
            throw new IllegalArgumentException("Введите p");
        p = Integer.parseInt(pProperty.get());
        if (p < 256)
            throw new IllegalArgumentException("p должно быть больше 256");
        if (p > 65535)
            throw new IllegalArgumentException("p должно быть меньше 65535");
        if (!MathUtils.isPrime(p))
            throw new IllegalArgumentException("p должно быть простым");

        if (xProperty.get().isEmpty())
            throw new IllegalArgumentException("Введите x");
        x = Integer.parseInt(xProperty.get());
        if (x <= 1)
            throw new IllegalArgumentException("x должен быть больше 1");
        if (x >= p - 1)
            throw new IllegalArgumentException("x должно быть меньше p - 1");

        Objects.requireNonNull(inputFile,
                "Сначала выберите исходный файл");

        ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
        IOService.decryptFile(inputFile, baos, p, x);
        byte[] result = baos.toByteArray();
        if (outputFile != null)
            Files.write(outputFile, result,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);

        StringBuilder stringResult = new StringBuilder();
        for (var b : result)
            stringResult.append(b & 0xFF).append(" ");
        resultProperty.set(stringResult.toString());
    }
}
