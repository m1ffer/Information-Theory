package com.example.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Application extends javafx.application.Application {
    AnnotationConfigApplicationContext context;
    @Override
    public void start(Stage stage){
        context =
                new AnnotationConfigApplicationContext("com.example.app");
        context.registerShutdownHook();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/choice-layout.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("ElGamal Encryption");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            context.close();
            throw new RuntimeException(e);
        }
    }
    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }
}
