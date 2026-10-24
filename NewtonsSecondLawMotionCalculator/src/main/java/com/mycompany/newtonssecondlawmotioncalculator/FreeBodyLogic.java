package com.mycompany.newtonssecondlawmotioncalculator;

import org.joml.Vector2d;

public class FreeBodyLogic {
    private Vector2d[] forceVectors;
    private Vector2d netForceVector;
    private Vector2d netAccelerationVector;
    private double mass;

    /**
     * Sets the force vectors and updates net force vector & net acceleration vector where possible
     * @param forceVectors The force vectors in Newtons
     */
    public void setForceVectors(Vector2d... forceVectors) {
        this.forceVectors = forceVectors;

        // Update net force vector & acceleration accordingly
        netForceVector = Calculator.calculateNetForceVector(forceVectors);

        if (mass > 0) {
            netAccelerationVector = Calculator.calculateNetAccelerationVector(netForceVector, mass);
        }
    }

    /**
     * Sets the mass and updates net acceleration vector where possible
     * @param mass The mass in kilograms
     */
    public void setMass(double mass) {
        this.mass = (mass > 0) ? mass : 0;

        if (mass > 0) {
            netAccelerationVector = Calculator.calculateNetAccelerationVector(netForceVector, mass);
        }
    }
}
