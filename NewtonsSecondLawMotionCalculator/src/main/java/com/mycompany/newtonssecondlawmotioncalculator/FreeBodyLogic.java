package com.mycompany.newtonssecondlawmotioncalculator;

import lombok.Getter;
import org.joml.Vector2d;

import java.util.*;

public class FreeBodyLogic {
    @Getter private static Map<String, VisualVector> visualVectors = new HashMap<>();
    @Getter private static Map<String, Vector2d> forceVectors = new HashMap<>();
    @Getter private static Vector2d netForceVector;
    @Getter private static Vector2d netAccelerationVector;
    @Getter private static double mass;
    @Getter private static double newtonPixelRatio;

    /**
     * Sets the force vector and updates net force vector & net acceleration vector where possible
     * @param uuid The UUID of the force vector
     * @param forceVector The force vector to add
     */
    public static void setForceVector(String uuid, Vector2d forceVector) {
        forceVectors.put(uuid, forceVector);
        updateValues();
    }

    /**
     * Removes the force vector and updates net force vector & net acceleration vector where possible
     * @param uuid The UUID of the force vector
     */
    public static void removeForceVector(String uuid) {
        forceVectors.remove(uuid);
        updateValues();
    }

    /**
     * Updates the values of netForceVector, newtonPixelRatio and netAccelerationVector
     */
    public static void updateValues() {
        List<Vector2d> forceVectorsList = new ArrayList<>(forceVectors.values());

        netForceVector = Calculator.calculateNetForceVector(forceVectorsList);
        newtonPixelRatio = Calculator.calculateNewtonToPixelRatio(forceVectorsList);

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
