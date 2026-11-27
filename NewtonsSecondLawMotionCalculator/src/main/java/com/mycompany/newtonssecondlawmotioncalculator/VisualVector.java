package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import lombok.Getter;
import org.joml.Vector2d;

import java.io.IOException;
import java.util.UUID;

@Getter
public class VisualVector {
    private String uuid;
    private final Vector2d vector;
    private VectorType vectorType;
    private FXMLLoader arrowLoader;
    private Arrow arrowController;
    private Pane arrowRoot;
    private Line arrowLine;
    private Polygon arrowHead;
    private Color color;
    private BooleanProperty hovering = new SimpleBooleanProperty(false);
    private VBox infoBox = null;

    public VisualVector(String uuid, Vector2d vector, VectorType vectorType, Color color) throws IOException {
        this.uuid = uuid;
        this.vector = vector;
        this.vectorType = vectorType;
        this.color = color;

        initializeArrow();
        initializeHovering();
    }

    public VisualVector(VectorType vectorType, Color color) throws IOException {
        this.uuid = UUID.randomUUID().toString();
        this.vector = new Vector2d();
        this.vectorType = vectorType;
        this.color = color;

        initializeArrow();
        initializeHovering();
    }

    /**
     * Initializes ArrowController and caches arrow nodes for the VisualVector
     * @throws IOException
     */
    public void initializeArrow() throws IOException {
        this.arrowLoader = App.getFXMLLoader("arrow");
        arrowLoader.load();
        this.arrowController = arrowLoader.getController();
        arrowController.setColor(color);

        this.arrowRoot = arrowLoader.getRoot();
        this.arrowLine = arrowController.getArrowLine();
        this.arrowHead = arrowController.getArrowHead();

        // Arrow root mouse events only trigger for arrow line and head
        arrowRoot.setPickOnBounds(false);
    }

    /**
     * Initializes the hovering system for the vector
     */
    public void initializeHovering() {
        arrowRoot.setOnMouseEntered(this::handleOnMouseEnter);
        arrowRoot.setOnMouseExited(this::handleOnMouseExit);
        arrowRoot.setOnMouseMoved(this::updateInfoPosition);
    }

    /**
     * Renders the arrow for the visual vector
     * @param originX The starting point X
     * @param originY The starting point Y
     * @param endX The end point X
     * @param endY The end point Y
     * @return The root of the arrow created
     * @throws IOException
     */
    public Pane render(double originX, double originY, double endX, double endY) throws IOException {
        arrowController.setPoints(originX, endX, originY, endY);
        arrowController.setColor(color);
        return arrowRoot;
    }

    /**
     * Handles a mouse enter event for the arrow root
     * @param event The mouse event
     */
    private void handleOnMouseEnter(MouseEvent event) {
        hovering.set(true);
        System.out.println("entered");

        try {
            // If we dont have a hover info box yet, we create it
            if (infoBox == null) {
                FXMLLoader hoverLoader = App.getFXMLLoader("vector_hover_details");
                infoBox = hoverLoader.load();

                // Prevents HBox from overriding layoutX/layoutY
                infoBox.setManaged(false);
                
                // Prevents popup from blocking cursor and flickering
                infoBox.setMouseTransparent(true);
            }
            
            HBox calculatorRoot = App.getCalculatorController().getRoot();
            
            // Checks before adding a duplicate
            if (!calculatorRoot.getChildren().contains(infoBox)) {
                calculatorRoot.getChildren().add(infoBox);
            }

            infoBox.autosize();

            updateInfoPosition(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles a mouse exit event for the arrow root
     * @param event The mouse event
     */
    private void handleOnMouseExit(MouseEvent event) {
        hovering.set(false);
        System.out.println("exited");
        
        // Remove hover info box from app root
        if (infoBox != null) {
            App.getCalculatorController().getRoot().getChildren().remove(infoBox);
        }
    }

    /**
     * Updates the position of the info box upon mouse move or mouse enter
     * @param event The mouse event
     */
    private void updateInfoPosition(MouseEvent event) {
        if (!hovering.get() || infoBox == null) {
            return;
        }
        double mousePosX = event.getSceneX();
        double mousePosY = event.getSceneY();

        double boundsX = infoBox.getWidth();
        double boundsY = infoBox.getHeight();

        infoBox.setLayoutX(mousePosX - boundsX / 2);
        infoBox.setLayoutY(mousePosY - boundsY + 35);
    }

    /**
     * Updates stored vector physics coordinates (used by net force & net acceleration on re-render).
     * @param newVector The new vector physics coordinates
     */
    public void setVector(Vector2d newVector) {
        if (newVector != null) {
            this.vector.set(newVector);
        }
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
