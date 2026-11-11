package com.mycompany.newtonssecondlawmotioncalculator;

import lombok.Getter;
import org.joml.Vector2d;

public class FreeBodyLogic {
    @Getter private static Vector2d[] forceVectors;
    @Getter private static Vector2d netForceVector;
    @Getter private static Vector2d netAccelerationVector;
    @Getter private static double mass;
    @Getter private static double newtonPixelRatio;

    /**
     * Sets the force vectors and updates net force vector & net acceleration vector where possible
     * @param forceVectorsToCalculate The force vectors in Newtons
     */
    public static void setForceVectors(Vector2d... forceVectorsToCalculate) {
        forceVectors = forceVectorsToCalculate;

        // Update net force vector, acceleration & newton pixel ratio accordingly
        netForceVector = Calculator.calculateNetForceVector(forceVectors);
        newtonPixelRatio = Calculator.calculateNewtonToPixelRatio(forceVectors);

        if (mass > 0) {
            netAccelerationVector = Calculator.calculateNetAccelerationVector(netForceVector, mass);
        }
    }

    /**
     * Sets the mass and updates net acceleration vector where possible
     * @param massToCalculate The mass in kilograms
     */
    public static void setMass(double massToCalculate) {
        mass = (massToCalculate > 0) ? massToCalculate : 0;

        if (mass > 0) {
            netAccelerationVector = Calculator.calculateNetAccelerationVector(netForceVector, mass);
        }
    }
}
