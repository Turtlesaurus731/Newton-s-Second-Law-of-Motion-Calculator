package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.joml.Random;
import org.joml.Vector2d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FreeBodyDiagram {

    private static Pane output;
    public static ObservableList<Pane> arrowNodes = FXCollections.observableList(new ArrayList<>());

    private static Arrow netAccelerationArrow;
    private static Arrow netForceArrow;

    public static void initialize() throws IOException {
        output = App.calculatorController.getOutput();

        netAccelerationArrow = loadArrow(Color.DARKRED);
        netForceArrow = loadArrow(Color.DARKBLUE);
    }

    private static Arrow loadArrow(Color color) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("arrow");
        loader.load();

        Arrow arrowController = loader.getController();
        arrowController.setColor(color);

        return arrowController;
    }

    private static void renderArrow(Arrow arrowController, Vector2d pixelVector) {
        if (pixelVector == null) {
            return;
        }

        final double START_X = output.getLayoutX() + output.getWidth() / 2;
        final double START_Y = output.getLayoutY() + output.getHeight() / 2;

        arrowController.setPoints(
                START_X,
                START_X - pixelVector.x(),
                START_Y,
                START_Y - pixelVector.y()
        );

        Pane arrowNode = arrowController.getArrowPane();
        arrowNodes.add(arrowNode);
        output.getChildren().add(arrowNode);
    }

    /**
     * Renders all the force vectors, net force vector, net acceleration vector
     */
    public static void render() throws IOException {
        resetDiagram();

        List<VisualVector> visualVectors = new ArrayList<>(FreeBodyLogic.getVisualVectors().values());

        // Avoids drawing net force arrow and net accel arrow if there is nothing
        if (visualVectors.isEmpty()) {
            return;
        }

        // Fetch all necessary data from FreeBodyLogic
        Vector2d netForceVector = FreeBodyLogic.getNetForceVector();
        Vector2d netAccelerationVector = FreeBodyLogic.getNetAccelerationVector();
        double newtonPixelRatio = FreeBodyLogic.getNewtonPixelRatio();

        // Draw all the force vectors accordingly with newton-pixel ratio
        for (VisualVector visualVector : visualVectors) {
            if (visualVector.getVectorType() != VectorType.FORCE) {
                continue;
            }

            Vector2d forceVector = visualVector.getVector();
            Vector2d pixelForceVector = Calculator.convertToPixelLength(newtonPixelRatio, forceVector);

            renderArrow(visualVector.getArrowController(), pixelForceVector);
        }

        // Make net force vector arrow
        Vector2d pixelNetForceVector = Calculator.convertToPixelLength(newtonPixelRatio, netForceVector);
        renderArrow(netForceArrow, pixelNetForceVector);

        // Make acceleration vector arrow
        Vector2d unitVector = pixelNetForceVector.normalize();
        Vector2d pixelNetAcceleration = unitVector.mul(200);
        if (CalculatorController.isSelected) {
            renderArrow(netAccelerationArrow, pixelNetAcceleration);
        }
    }

    public static void resetDiagram() {
        for (Pane arrowNode : arrowNodes) {
            output.getChildren().remove(arrowNode);
        }

        arrowNodes.clear();
    }
}
