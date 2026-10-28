package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.scene.canvas.Canvas;
import org.joml.Vector2d;

public class FreeBodyDiagram {
    private static CalculatorController calculatorController = App.getCalculatorController();
    private static Canvas output = calculatorController.getOutput();

    /**
     * Renders all the force vectors, net force vector, net acceleration vector and mass
     */
    public void render() {
        Vector2d[] forceVectors = FreeBodyLogic.getForceVectors();
        Vector2d netForceVector = FreeBodyLogic.getNetForceVector();
        Vector2d netAccelerationVector = FreeBodyLogic.getNetAccelerationVector();
        double mass = FreeBodyLogic.getMass();
    }
}
