package com.mycompany.newtonssecondlawmotioncalculator;

import org.joml.Vector2d;
import java.util.Arrays;

public class Calculator {
    /**
     * Calculates the net force vector given a multitude of force vectors
     * @param forceVectors The force vectors in Newtons
     * @return The net force vector in Newtons
     */
    public static Vector2d calculateNetForceVector(Vector2d... forceVectors) {
        Vector2d netForceVector = new Vector2d();
        Arrays.stream(forceVectors).forEach(netForceVector::add);
        return netForceVector;
    }

    /**
     * Calculates the net acceleration vector given a net force vector and the mass. [ a = F / m ]
     * @param netForceVector The net force vector in Newtons
     * @param mass The mass in kilograms
     * @return The net acceleration vector in m/s^2
     */
    public static Vector2d calculateNetAccelerationVector(Vector2d netForceVector, double mass) {
        return netForceVector.div(mass);
    }

    /**
     * Calculates the Newton-Pixel ratio for the force vector lines
     * @param forceVectors The force vectors that will be rendered in canvas
     * @return The Newton-Pixel ratio that fits all the force vectors in the canvas
     */
    public static double calculateNewtonToPixelRatio(Vector2d[] forceVectors) {
        if (forceVectors.length <= 0) {
            // TODO: Think of using exceptions
            System.out.println("No force vectors provided to calculate ratio");
            return -1;
        }

        CalculatorController calculatorController = App.getCalculatorController();

        final double CANVAS_SIZE_X = calculatorController.getOutput().getWidth();
        final double CANVAS_SIZE_Y = calculatorController.getOutput().getHeight();
        final double CANVAS_CENTER = CANVAS_SIZE_X / 2;
        final double VECTOR_COVERAGE = 80; // How much of the canvas the maximum vector covers in %
        final double PIXEL_LENGTH_BASE = CANVAS_SIZE_X * (VECTOR_COVERAGE / 100);

        Vector2d maximumForceVector = forceVectors[0];

        for (Vector2d forceVector : forceVectors) {
            if (forceVector.length() > maximumForceVector.length()) {
                maximumForceVector = forceVector;
            }
        }

        return PIXEL_LENGTH_BASE / maximumForceVector.length();
    }

    /**
     * Converts a force vector's coordinates from Newtons to Pixels
     * @param newtonToPixelRatio The Newton-Pixel ratio
     * @param forceVector The force vector to convert
     * @return The force vector with its coordinates converted to pixels
     */
    public static Vector2d convertToPixelLength(double newtonToPixelRatio, Vector2d forceVector) {
        return forceVector.mul(newtonToPixelRatio);
    }
}
