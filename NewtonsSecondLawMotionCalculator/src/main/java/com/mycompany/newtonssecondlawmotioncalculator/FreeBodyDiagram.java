package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.Getter;
import org.joml.Random;
import org.joml.Vector2d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FreeBodyDiagram {
    private static Pane output;
    public static ObservableList<Pane> arrowNodes = FXCollections.observableList(new ArrayList<>());

    @Getter private static double newtonPixelRatio;

    private static VisualVector netAccelerationVisualVector;
    private static VisualVector netForceVisualVector;

    @Getter private static VisualVector componentVectorX;
    @Getter private static VisualVector componentVectorY;

    public static void initialize() throws IOException {
        output = App.calculatorController.getOutput();

        netAccelerationVisualVector = new VisualVector(VectorType.NET_ACCELERATION, Color.ORANGE);
        netForceVisualVector = new VisualVector(VectorType.NET_FORCE, Color.GREEN);

        componentVectorX = new VisualVector(VectorType.FORCE, Color.RED);
        componentVectorY = new VisualVector(VectorType.FORCE, Color.BLUE);

        componentVectorX.getArrowRoot().setOpacity(0.5);
        componentVectorY.getArrowRoot().setOpacity(0.5);
        componentVectorX.getArrowRoot().setMouseTransparent(true);
        componentVectorY.getArrowRoot().setMouseTransparent(true);
    }

    public static void renderArrow(VisualVector visualVector) {
        List<Vector2d> forceVectors = FreeBodyLogic.getTotalForceVectors();
        newtonPixelRatio = Calculator.calculateNewtonToPixelRatio(forceVectors);

        if (newtonPixelRatio <= 0 || (visualVector.getVectorType() == VectorType.NET_ACCELERATION && FreeBodyLogic.getMass() <= 0)) {
            output.getChildren().remove(visualVector.getArrowRoot());
            return;
        }

        Vector2d pixelVector = Calculator.convertToPixelLength(newtonPixelRatio, visualVector.getVector());
        if (visualVector.getVectorType() == VectorType.NET_ACCELERATION) {
            pixelVector.mul(FreeBodyLogic.getMass() * Config.RENDERED_ACCELERATION_VECTOR_RATIO);
        }

        if (pixelVector.length() <= 0) {
            output.getChildren().remove(visualVector.getArrowRoot());
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

            renderArrow(visualVector);
        }

        // Sync net force & net acceleration visual vectors with FreeBodyLogic
        updateNetVectors();

        // Render net force visual vector
        Vector2d pixelNetForceVector = Calculator.convertToPixelLength(newtonPixelRatio, netForceVector);
        renderArrow(netForceVisualVector);

        // Render net acceleration visual vector
        if (CalculatorController.netAccelerationIsSelected) {
            renderArrow(netAccelerationVisualVector);
        }
    }

    /**
     * Updates and syncs the inner physics vectors of netForceVisualVector and netAccelerationVisualVector
     * with FreeBodyLogic's current netForceVector and netAccelerationVector.
     */
    public static void updateNetVectors() {
        Vector2d netForce = FreeBodyLogic.getNetForceVector();
        Vector2d netAccel = FreeBodyLogic.getNetAccelerationVector();

        netForceVisualVector.setVector(netForce != null ? netForce : new Vector2d());
        netAccelerationVisualVector.setVector(netAccel != null ? netAccel : new Vector2d());
    }

    public static void resetDiagram() {
        for (Pane arrowNode : arrowNodes) {
            output.getChildren().remove(arrowNode);
        }

        arrowNodes.clear();
        updateNetVectors();
    }
}
