module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;

    // Spring
    requires spring.context;
    requires spring.beans;
    requires spring.core;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires static lombok;

    opens com.example.app to javafx.fxml, spring.core, spring.beans;
    opens com.example.app.controllers to javafx.fxml, spring.core, spring.beans;
    opens com.example.app.models to javafx.fxml, spring.core, spring.beans;
    exports com.example.app;
}