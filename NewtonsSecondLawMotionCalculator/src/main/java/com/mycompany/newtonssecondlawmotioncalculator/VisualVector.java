package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
    private Pane arrowNode;
    private Color color;

    public VisualVector(String uuid, Vector2d vector, VectorType vectorType, Color color) throws IOException {
        this.uuid = uuid;
        this.vector = vector;
        this.vectorType = vectorType;
        this.color = color;

        this.arrowLoader = App.getFXMLLoader("arrow");
        arrowLoader.load();

        this.arrowNode = arrowLoader.getRoot();
        this.arrowController = arrowLoader.getController();
    }

    public Pane render(double originX, double originY, double endX, double endY) throws IOException {
        arrowController.setPoints(originX, endX, originY, endY);
        arrowController.setColor(color);
        return arrowNode;
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
}
