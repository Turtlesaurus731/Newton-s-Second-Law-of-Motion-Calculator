package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2d;

import java.io.IOException;
import java.util.UUID;

@Getter
public class VisualVector {
    private String uuid;
    private final Vector2d vector;
    @Setter private VectorType vectorType;
    private FXMLLoader arrowLoader;
    private Arrow arrowController;
    private Pane arrowRoot;
    private Line arrowLine;
    private Polygon arrowHead;
    private Color color;
    private BooleanProperty hovering = new SimpleBooleanProperty(false);
    private VBox infoBox = null;
    @Setter private int order;
    private Timeline hoverPulseAnimation;
    private final Scale arrowHeadScale = new Scale(1, 1);

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

        arrowRoot.setCursor(Cursor.HAND);

        // Arrow root mouse events only trigger for arrow line and head
        arrowRoot.setPickOnBounds(false);
    }

    /**
     * Initializes the hovering system for the vector
     */
    public void initializeHovering() {
        // Initialize handlers
        arrowRoot.setOnMouseEntered(this::handleOnMouseEnter);
        arrowRoot.setOnMouseExited(this::handleOnMouseExit);
        arrowRoot.setOnMouseMoved(this::updateInfoPosition);
        arrowRoot.setOnMouseClicked(this::selectVector);

        // Initialize hover animation
        hoverPulseAnimation = new Timeline(new KeyFrame(Duration.millis(650),
                new KeyValue(arrowLine.strokeWidthProperty(), 8.0, Interpolator.EASE_BOTH),
                new KeyValue(arrowHeadScale.xProperty(), 1.35, Interpolator.EASE_BOTH),
                new KeyValue(arrowHeadScale.yProperty(), 1.35, Interpolator.EASE_BOTH)));
        hoverPulseAnimation.setCycleCount(Timeline.INDEFINITE);
        hoverPulseAnimation.setAutoReverse(true);
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

            // Sets the component vectors
            VisualVector componentX = FreeBodyDiagram.getComponentVectorX();
            VisualVector componentY = FreeBodyDiagram.getComponentVectorY();

            componentX.setVector(new Vector2d(vector.x(), 0));
            componentY.setVector(new Vector2d(0, vector.y()));

            FreeBodyDiagram.renderArrow(componentX);
            FreeBodyDiagram.renderArrow(componentY);

            infoBox.autosize();

            // Pulse animation start
            arrowLine.setStrokeWidth(5.0);
            arrowHeadScale.setX(1);
            arrowHeadScale.setY(1);
            arrowHeadScale.setPivotX(arrowLine.getEndX());
            arrowHeadScale.setPivotY(arrowLine.getEndY());
            arrowHead.getTransforms().setAll(arrowHeadScale);
            hoverPulseAnimation.playFromStart();

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

        // Remove hover info box from app root
        if (infoBox != null) {
            App.getCalculatorController().getRoot().getChildren().remove(infoBox);
        }

        // Hide the component vectors
        VisualVector componentX = FreeBodyDiagram.getComponentVectorX();
        VisualVector componentY = FreeBodyDiagram.getComponentVectorY();

        componentX.setVector(new Vector2d());
        componentY.setVector(new Vector2d());

        FreeBodyDiagram.renderArrow(componentX);
        FreeBodyDiagram.renderArrow(componentY);

        // Pulse animation stop
        hoverPulseAnimation.stop();
        arrowLine.setStrokeWidth(5.0);
        arrowHeadScale.setX(1);
        arrowHeadScale.setY(1);
        arrowHead.getTransforms().remove(arrowHeadScale);
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
     * Selects the vector in the list view after being clicked
     * @param event The mouse event
     */
    private void selectVector(MouseEvent event) {
        ListView<VisualVector> forceVectorListView = App.getCalculatorController().getForceVectorListView();

        // Select or deselect depending on if hte vector is already chosen in the list view
        if (order == forceVectorListView.getSelectionModel().getSelectedIndex()) {
            forceVectorListView.getSelectionModel().select(null);
        } else if (order < forceVectorListView.getItems().size() && order >= 0) {
            forceVectorListView.getSelectionModel().select(order);
        }
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
        return String.format("<%.1f, %.1f> %s Vector", vector.x(), vector.y(), vectorType.getName());
    }
}
