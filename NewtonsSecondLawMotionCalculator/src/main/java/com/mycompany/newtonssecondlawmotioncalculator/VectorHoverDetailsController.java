package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VectorHoverDetailsController {

    @FXML
    private VBox infoBox;

    @FXML
    private VBox rootHoverDetails;

    @FXML
    private Label vectorAngleLabel;

    @FXML
    private Label vectorComponentsLabel;

    @FXML
    private Label vectorMagnitudeLabel;

    @FXML
    private Label vectorORderInListLabel;

    @FXML
    private Label vectorTitleLabel;

    public void setDarkMode(boolean darkMode) {
        String dark = getClass().getResource("/vector-hover-dark.css").toExternalForm();
        String light = getClass().getResource("/vector-hover-light.css").toExternalForm();
        rootHoverDetails.getStylesheets().removeAll(dark, light);
        if (darkMode) {
            rootHoverDetails.getStylesheets().add(dark);
        } else {
            rootHoverDetails.getStylesheets().add(light);
        }
    }
}