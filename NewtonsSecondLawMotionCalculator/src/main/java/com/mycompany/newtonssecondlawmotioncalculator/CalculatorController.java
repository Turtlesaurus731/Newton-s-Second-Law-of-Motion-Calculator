/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.newtonssecondlawmotioncalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class CalculatorController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private Canvas output;
    @FXML
    private ListView<?> forceVectorListView;
    @FXML
    private TextField xForceVectorTextField;
    @FXML
    private TextField yForceVectorTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button resetButton;
    @FXML
    private TextField massTextField;
    @FXML
    private CheckBox netAccelerationCheckBox;
    @FXML
    private Button infoButton;

    //Temporary before enzo fix on his end
    @FXML
    private CheckBox darkModeToggleCheckBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onActionAddButton(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    private void onActionRemoveButton(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    private void onActionResetButton(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    private void onActionNetAccelerationCheckBox(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    private void onActionDarkModeToggleCheckBox(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    public void onActionInfoButton(ActionEvent actionEvent) {
        // TODO
    }
    
}
