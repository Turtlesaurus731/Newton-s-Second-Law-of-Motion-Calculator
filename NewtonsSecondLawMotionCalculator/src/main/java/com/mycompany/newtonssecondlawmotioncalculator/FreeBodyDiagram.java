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

    private static VisualVector netAccelerationVisualVector;
    private static VisualVector netForceVisualVector;

    public static void initialize() throws IOException {
        output = App.calculatorController.getOutput();

        netAccelerationVisualVector = new VisualVector(VectorType.NET_ACCELERATION, Color.RED);
        netForceVisualVector = new VisualVector(VectorType.NET_FORCE, Color.BLUE);
    }

    private static Arrow loadArrow(Color color) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("arrow");
        loader.load();

        Arrow arrowController = loader.getController();
        arrowController.setColor(color);
        arrowController.getArrowPane().setPickOnBounds(false);

        return arrowController;
    }

    private static void renderArrow(VisualVector visualVector, Vector2d pixelVector) {
        if (pixelVector == null) {
            return;
        }

        final double START_X = output.getLayoutX() + output.getWidth() / 2;
        final double START_Y = output.getLayoutY() + output.getHeight() / 2;

        visualVector.getArrowController().setPoints(
                START_X,
                START_X - pixelVector.x(),
                START_Y,
                START_Y - pixelVector.y()
        );

        Pane arrowNode = visualVector.getArrowRoot();
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

            renderArrow(visualVector, pixelForceVector);
        }

        // Update net force & net acceleration visual vectors with new physics vectors
        netForceVisualVector.setVector(netForceVector);
        netAccelerationVisualVector.setVector(netAccelerationVector);

        // Render net force visual vector
        Vector2d pixelNetForceVector = Calculator.convertToPixelLength(newtonPixelRatio, netForceVector);
        renderArrow(netForceVisualVector, pixelNetForceVector);

        // Render net acceleration visual vector
        Vector2d pixelNetAccelerationVector = null;

        if (CalculatorController.netAccelerationIsSelected) {
            pixelNetAccelerationVector = new Vector2d(pixelNetForceVector).mul(Config.RENDERED_ACCELERATION_VECTOR_RATIO);
            renderArrow(netAccelerationVisualVector, pixelNetAccelerationVector);
        }
    }

    public static void resetDiagram() {
        for (Pane arrowNode : arrowNodes) {
            output.getChildren().remove(arrowNode);
        }

        arrowNodes.clear();
    }
}
