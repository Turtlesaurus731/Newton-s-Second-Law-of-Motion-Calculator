package com.mycompany.newtonssecondlawmotioncalculator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class VectorHoverDetailsController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CalculatorController calculatorController = App.getCalculatorController();
        boolean isDark = calculatorController != null
                && calculatorController.getDarkModeToggleCheckBox() != null
                && calculatorController.getDarkModeToggleCheckBox().isSelected();
        setDarkMode(isDark);
    }

    /**
     * Switches the hover card stylesheet between dark and light and reapplies the css
     * @param darkMode {true} to apply dark stylesheet & {false} to apply light stylesheet
     */
    public void setDarkMode(boolean darkMode) {
        if (rootHoverDetails == null) {
            return;
        }
        String dark = getClass().getResource("/vector-hover-dark.css").toExternalForm();
        String light = getClass().getResource("/vector-hover-light.css").toExternalForm();
        rootHoverDetails.getStylesheets().removeAll(dark, light);
        if (darkMode) {
            rootHoverDetails.getStylesheets().add(dark);
        } else {
            rootHoverDetails.getStylesheets().add(light);
        }
        rootHoverDetails.applyCss();
    }
}