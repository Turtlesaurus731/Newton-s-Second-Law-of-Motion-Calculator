/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.newtonssecondlawmotioncalculator;

import java.net.URL;
import java.util.Objects;
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
import lombok.Getter;

/**
 * FXML Controller class
 *
 * @author HP
 */
@Getter
public class CalculatorController implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private Canvas output;
    @FXML
    private ListView<String> forceVectorListView;
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

    }

    @FXML
    private void onActionAddButton(ActionEvent actionEvent) {
        String x = xForceVectorTextField.getText();
        String y = yForceVectorTextField.getText();
        forceVectorListView.getItems().addAll("<" + x + "," + y + ">"  + " Vector");
    }

    @FXML
    private void onActionRemoveButton(ActionEvent actionEvent) {
        int vector = forceVectorListView.getSelectionModel().getSelectedIndex();
        forceVectorListView.getItems().remove(vector);
    }

    @FXML
    private void onActionResetButton(ActionEvent actionEvent) {
        forceVectorListView.getItems().clear();
    }

    @FXML
    private void onActionNetAccelerationCheckBox(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    private void onActionDarkModeToggleCheckBox(ActionEvent actionEvent) {
        if (darkModeToggleCheckBox.isSelected()) {
            if (!root.getStylesheets().contains(getClass().getResource("/dark-theme.css").toExternalForm())) {
                root.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());
            }
        } else {
            root.getStylesheets().remove(getClass().getResource("/dark-theme.css").toExternalForm());
        }
    }

    @FXML
    public void onActionInfoButton(ActionEvent actionEvent) {
        // TODO
    }
    
}
