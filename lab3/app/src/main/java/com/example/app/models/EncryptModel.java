package com.example.app.models;

import crypto.elgamal.ElGamalService;
import crypto.io.IOService;
import crypto.math.Factorization;
import crypto.math.MathUtils;
import crypto.primitive.PrimitiveRootService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class EncryptModel {
    @Getter
    private final StringProperty pProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty xProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty yProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty kProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty inputFileProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty outputFileProperty = new SimpleStringProperty("");
    @Getter
    private final StringProperty resultProperty = new SimpleStringProperty("");
    @Getter
    private final ObservableList<Integer> gList = FXCollections.observableArrayList();
    @Getter
    private final BooleanProperty gReady = new SimpleBooleanProperty(false);

    private int p;
    private List<Integer> factors;
    private int x;
    private int g;
    private int y;
    private Integer startK;
    private Path inputFile;
    private Path outputFile;

    public void computeG(){
        if (!gList.isEmpty())
            return;
        if (pProperty.get().isEmpty())
            throw new IllegalArgumentException("Введите p");
        p = Integer.parseInt(pProperty.get());
        if (p < 256)
            throw new IllegalArgumentException("p должно быть больше 256");
        if (p > 65535)
            throw new IllegalArgumentException("p должно быть меньше 65535");
        if (!MathUtils.isPrime(p))
            throw new IllegalArgumentException("p должно быть простым");
        factors = Factorization.primeFactors(p - 1);
        int g = PrimitiveRootService.findOnePrimitiveRoot(p, factors);
        List<Integer> allG = PrimitiveRootService.findAllPrimitiveRoots(p, g, factors);
        Collections.sort(allG);
        gList.setAll(allG);
        gReady.set(true);
    }

    public void resetG(){
        gList.clear();
        gReady.set(false);
        resetY();
    }

    public void resetY(){
        yProperty.set("");
        resultProperty.set("");
    }

    public void setInputFile(Path p){
        inputFile = p;
        inputFileProperty.set(inputFile.toString());
        resultProperty.set("");
    }
    public void setOutputFile(Path p){
        outputFile = p;
        outputFileProperty.set(outputFile.toString());
    }

    public void encrypt(Integer g) throws IOException {
        Objects.requireNonNull(g,
                "Сначала выберите g");

        if (xProperty.get().isEmpty())
            throw new IllegalArgumentException("Введите x");
        x = Integer.parseInt(xProperty.get());
        if (x <= 1)
            throw new IllegalArgumentException("x должен быть больше 1");
        if (x >= p - 1)
            throw new IllegalArgumentException("x должно быть меньше p - 1");

        if (kProperty.get().isEmpty())
            startK = null;
        else {
            startK = Integer.parseInt(kProperty.get());
            if (startK <= 1)
                throw new IllegalArgumentException("k должно быть больше 1");
            if (startK >= p - 1)
                throw new IllegalArgumentException("k должно быть меньше p - 1");
            if (!Factorization.isCoprimeWithPhi(startK, factors))
                throw new IllegalArgumentException("k должно быть взаимно простым с p - 1");
        }

        Objects.requireNonNull(inputFile,
                "Сначала выберите исходный файл");

        y = ElGamalService.generateY(p, g, x);
        yProperty.set(String.valueOf(y));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOService.encryptFile(inputFile, baos, p, g, y, startK, factors);
        byte[] result = baos.toByteArray();
        if (outputFile != null)
            Files.write(outputFile, result,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);

        StringBuilder stringResult = new StringBuilder();
        for(int i = 0; i < result.length; i += 2){
            int a = ((result[i] & 0xFF) << 8) | (result[i + 1] & 0xFF);
            stringResult.append(a).append(" ");
        }
        resultProperty.set(stringResult.toString());
    }
}
