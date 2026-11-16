package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Getter private static Scene scene;
    @Getter protected static CalculatorController calculatorController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader calculatorFXML = getFXMLLoader("calculator");
        Parent root = calculatorFXML.load();
        calculatorController = calculatorFXML.getController();

        FreeBodyDiagram.initialize();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(getFXMLLoader(fxml).load());
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            return fxmlLoader;
        } catch(Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        launch();
    }

}