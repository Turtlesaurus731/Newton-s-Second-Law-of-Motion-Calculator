package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.Getter;
import org.joml.Vector2d;

@Getter
public class VisualVector {
    private String uuid;
    private Vector2d vector;
    private Line line;
    private VectorType vectorType;

    public VisualVector(String uuid, Vector2d vector, VectorType vectorType) {
        this.uuid = uuid;
        this.vector = vector;
        this.vectorType = vectorType;
        line = null;
    }

    public Line render(double originX, double originY, double pixelX, double pixelY, Color color) {
        line = new Line(originX, originY, originX + pixelX, originY + pixelY);
        line.setStroke(color);

        // TODO: Add hovering over line logic to see extra information

        // TODO: Add clicking logic to select in list

        return line;
    }

    @Override
    public String toString() {
        System.out.println("ran to string");
        System.out.println(String.format("<%.1f, %.1f> %s Vector", vector.x(), vector.y(), vectorType.getName()));
        return String.format("<%.1f, %.1f> %s Vector", vector.x(), vector.y(), vectorType.getName());
    }
}
