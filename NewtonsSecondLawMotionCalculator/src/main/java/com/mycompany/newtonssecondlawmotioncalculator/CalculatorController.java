/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.newtonssecondlawmotioncalculator;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.UnaryOperator;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.converter.DoubleStringConverter;
import lombok.Getter;
import org.joml.Vector2d;

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
    private Pane output;
    @FXML
    private ListView<VisualVector> forceVectorListView;
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
        // Detect changes in list view and re-render diagram
        forceVectorListView.getItems().addListener((ListChangeListener<? super VisualVector>) change -> {
            FreeBodyDiagram.render();
        });

        // Filter user input for number fields
        UnaryOperator<TextFormatter.Change> numberFilter = change -> {
            String newText = change.getControlNewText();
            
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            
            return null;
        };

        xForceVectorTextField.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter(), null, numberFilter));
        yForceVectorTextField.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter(), null, numberFilter));
        massTextField.setTextFormatter(new TextFormatter<Double>(new DoubleStringConverter(), null, numberFilter));
    }

    @FXML
    private void onActionAddButton(ActionEvent actionEvent) {
        String xString = xForceVectorTextField.getText();
        String yString = yForceVectorTextField.getText();

        double x = Double.valueOf(xString);
        double y = Double.valueOf(yString);

        String uuid = UUID.randomUUID().toString();
        Vector2d vector = new Vector2d(x, y);

        VisualVector visualVector = new VisualVector(uuid, vector, VectorType.FORCE);

        FreeBodyLogic.getVisualVectors().put(visualVector.getUuid(), visualVector);
        FreeBodyLogic.setForceVector(uuid, visualVector.getVector());

        forceVectorListView.getItems().add(visualVector);
    }

    @FXML
    private void onActionRemoveButton(ActionEvent actionEvent) {
        int selectedIdx = forceVectorListView.getSelectionModel().getSelectedIndex();

        if (selectedIdx == -1) {
            return;
        }

        VisualVector selectedVisualVector = forceVectorListView.getItems().get(selectedIdx);

        // Remove all outside references to the visual vector
        FreeBodyLogic.getVisualVectors().remove(selectedVisualVector.getUuid());
        FreeBodyLogic.removeForceVector(selectedVisualVector.getUuid());

        // Remove the visual vector from the list view (and de-render it)
        forceVectorListView.getItems().remove(selectedVisualVector);
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
        Image image = new Image("/InformationImage01.png");
        ImageView icon = new ImageView(image);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO");
        alert.setHeaderText(null);

        try {
            HBox loadedInfoContent = FXMLLoader.load(getClass().getResource("information.fxml"));
            alert.getDialogPane().setExpandableContent(loadedInfoContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        alert.setGraphic(icon);
        alert.setContentText("temp");
        alert.showAndWait();
    }
    
}
