package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.joml.Random;
import org.joml.Vector2d;

public class FreeBodyDiagram {

    private static CalculatorController calculatorController = App.getCalculatorController();
    private static Canvas output = calculatorController.getOutput();

    /**
     * Renders all the force vectors, net force vector, net acceleration vector
     */
    public void render() {
        // TODO: Replace with user-selected colors (?)
        Random rand = new Random();

        // Fetch all necessary data from FreeBodyLogic
        Vector2d[] forceVectors = FreeBodyLogic.getForceVectors();
        Vector2d netForceVector = FreeBodyLogic.getNetForceVector();
        Vector2d netAccelerationVector = FreeBodyLogic.getNetAccelerationVector();
        double newtonPixelRatio = FreeBodyLogic.getNewtonPixelRatio();

        // Draw all the force vectors accordingly with newton-pixel ratio
        for (Vector2d forceVector : forceVectors) {
            Vector2d pixelForceVector = Calculator.convertToPixelLength(newtonPixelRatio, forceVector);

            // Get a random color for the force vector
            Color color = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

            drawVector(pixelForceVector, color);
        }

        // Draw the acceleration vector at a static amount of pixel length
        if (calculatorController.getNetAccelerationCheckBox().isSelected()) {
            Vector2d pixelNetForceVector = Calculator.convertToPixelLength(newtonPixelRatio, netForceVector);
            Vector2d normalizedAccelerationVector = netAccelerationVector.normalize();

            double pixelLengthNetForce = pixelNetForceVector.length();
            double pixelLengthAcceleration = pixelLengthNetForce * Config.MAX_ACCELERATION_VECTOR_COVERAGE;

            Vector2d pixelAccelerationVector = normalizedAccelerationVector.mul(pixelLengthAcceleration);

            // TODO: replace hardcoded gray color with user-picked color (?)
            drawVector(pixelAccelerationVector, Color.GRAY);
        }
    }

    /**
     * Draws a vector in the center of the canvas/output
     */
    public void drawVector(Vector2d pixelVector, Color color) {
        // Constants for the line coordinates
        final double START_X = output.getWidth() / 2;
        final double START_Y = output.getHeight() / 2;
        final double END_X = output.getWidth() + pixelVector.x();
        final double END_Y = output.getWidth() + pixelVector.y();

        // Create the line
        GraphicsContext graphicsContext = output.getGraphicsContext2D();

        graphicsContext.setStroke(color);
        graphicsContext.strokeLine(START_X, START_Y, END_X, END_Y);
    }
}
