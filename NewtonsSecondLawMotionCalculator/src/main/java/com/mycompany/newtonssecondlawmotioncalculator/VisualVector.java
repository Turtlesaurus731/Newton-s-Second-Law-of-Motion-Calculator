package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import lombok.Getter;
import org.joml.Vector2d;

import java.io.IOException;

@Getter
public class VisualVector {
    private String uuid;
    private final Vector2d vector;
    private VectorType vectorType;
    private FXMLLoader arrowLoader;
    private Arrow arrowController;
    private Pane arrowRoot;
    private Line arrowLine;
    private Polygon arrowHead;
    private Color color;
    private BooleanProperty hovering = new SimpleBooleanProperty(false);
    private VBox hoverInfoBox = null;

    public VisualVector(String uuid, Vector2d vector, VectorType vectorType, Color color) throws IOException {
        this.uuid = uuid;
        this.vector = vector;
        this.vectorType = vectorType;
        this.color = color;

        this.arrowLoader = App.getFXMLLoader("arrow");
        arrowLoader.load();
        this.arrowController = arrowLoader.getController();

        this.arrowRoot = arrowLoader.getRoot();
        this.arrowLine = arrowController.getArrowLine();
        this.arrowHead = arrowController.getArrowHead();
    }

    public Pane render(double originX, double originY, double endX, double endY) throws IOException {
        arrowController.setPoints(originX, endX, originY, endY);
        arrowController.setColor(color);
        return arrowRoot;
    }

    public void initializeHovering() {
        arrowLine.setOnMouseEntered(new HoverEnterHandler());
        arrowLine.setOnMouseExited(new HoverExitHandler());

        arrowHead.setOnMouseEntered(new HoverEnterHandler());
        arrowHead.setOnMouseExited(new HoverExitHandler());

        // Update hover panel position
        arrowRoot.getScene().setOnMouseMoved(event -> {
            if (!hovering.get() || hoverInfoBox == null) {
                return;
            }

            double mousePosX = event.getSceneX();
            double mousePosY = event.getSceneY();

            hoverInfoBox.setLayoutX(mousePosX);
            hoverInfoBox.setLayoutY(mousePosY);
        });
    }

    /**
     * Returns a separate copy of the stored vector in visual vector to prevent mutations
     * @return The copy of the stored vector
     */
    public Vector2d getVector() {
        return new Vector2d(vector);
    }

    @Override
    public String toString() {
        System.out.println("ran to string");
        System.out.println(String.format("<%.1f, %.1f> %s Vector", vector.x(), vector.y(), vectorType.getName()));
        return String.format("<%.1f, %.1f> %s Vector", vector.x(), vector.y(), vectorType.getName());
    }

    private class HoverEnterHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            hovering.set(true);

            FXMLLoader hoverLoader = App.getFXMLLoader("vector_hover_details");

            try {
                hoverInfoBox = hoverLoader.load();

                HBox calculatorRoot = App.getCalculatorController().getRoot();
                calculatorRoot.getChildren().add(hoverInfoBox);
            } catch (Exception e) {
                // Ignored
            }
        }
    }

    private class HoverExitHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            hovering.set(false);

            HBox calculatorRoot = App.getCalculatorController().getRoot();
            calculatorRoot.getChildren().remove(hoverInfoBox);
        }
    }
}
