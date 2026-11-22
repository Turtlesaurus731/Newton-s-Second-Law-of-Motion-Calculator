/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.newtonssecondlawmotioncalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * FXML Controller class
 *
 * @author danie
 */
public class Arrow implements Initializable {

    @FXML
    private Pane arrowPane;
    @FXML
    private Line arrowLine;
    @FXML
    private Polygon arrowHead;

    private double arrowHeadLength = 10;
    private double arrowHeadAngle = Math.toRadians(60);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        arrowLine.startXProperty().addListener(obs -> updateArrowHead());
    }

    public void setPoints(double startX, double endX, double startY, double endY) {
        arrowLine.setStartX(startX);
        arrowLine.setEndX(endX);
        arrowLine.setStartY(startY);
        arrowLine.setEndY(endY);

        updateArrowHead();
    }

    public void setColor(Color color) {
        arrowLine.setStroke(color);
        arrowHead.setFill(color);
    }

    public void updateArrowHead() {
        double startX = arrowLine.getStartX();
        double endX = arrowLine.getEndX();
        double startY = arrowLine.getStartY();
        double endY = arrowLine.getEndY();

        double angle = Math.atan2(endY - startY, endX - startX);

        double headHeight = arrowHeadLength * Math.cos(arrowHeadAngle);
        double tipX = endX + headHeight * Math.cos(angle);
        double tipY = endY + headHeight * Math.sin(angle);

        double x1 = endX - arrowHeadLength * Math.cos(angle - arrowHeadAngle);
        double y1 = endY - arrowHeadLength * Math.sin(angle - arrowHeadAngle);
        double x2 = endX - arrowHeadLength * Math.cos(angle + arrowHeadAngle);
        double y2 = endY - arrowHeadLength * Math.sin(angle + arrowHeadAngle);

        arrowHead.getPoints().setAll(tipX, tipY, x1, y1, x2, y2);
    }

    public void setArrowHeadLength(double arrowHeadLength) {
        this.arrowHeadLength = arrowHeadLength;
        updateArrowHead();
    }

    /**
     * Sets the arrow head angle and recalculates its shape)
     * @param arrowHeadAngle The arrow head angle in degrees
     */
    public void setArrowHeadAngle(double arrowHeadAngle) {
        this.arrowHeadAngle = arrowHeadAngle;
        updateArrowHead();
    }

    public Pane getRoot() {
        return arrowPane;
    }
}
