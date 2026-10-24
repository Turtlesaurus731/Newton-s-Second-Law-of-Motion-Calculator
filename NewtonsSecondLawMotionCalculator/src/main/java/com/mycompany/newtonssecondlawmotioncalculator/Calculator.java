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
    public static double calculateNewtonToPixelRatio(Vector2d forceVectors) {
        return 0;
    }
}
