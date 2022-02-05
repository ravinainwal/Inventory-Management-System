package com.example.inventorymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Place your name here
 */
public class AddPartFormController {
    Part modifyPart; // Part Class object to save the part which will get modified later
    Boolean editData = false, inHouse = true; // Adding new data or editing previous data checker boolean variable
    int asProID = -1;// ID of associated part, default -1 for individual parts
    int id;
    String partName;
    int inv;
    Double price;
    int max;
    int min;
    @FXML
    private TextField partCompanyNameField;
    @FXML
    private TextField partPriceField;
    @FXML
    private TextField partMaxField;
    @FXML
    private TextField partMinField;
    @FXML
    private TextField partNameTextField;
    @FXML
    private TextField partLnvField;
    @FXML
    private TextField partIDTextField;
    @FXML
    private TextField partMachineIDField;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private Label partCompanyNameLabel;
    @FXML
    private Label partMachineIDLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label nameLabel2;
    @FXML
    private Label nameLabel3;
    @FXML
    private Label nameLabel1;
    private Inventory inventoryController;//To change the control according to the currently available form

    @FXML
    /**
     * Cancel Button to get back to the main window
     */
    void cancelButtonAction(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    /**
     * outsource Radio Button to get the company name field
     */
    void outsourceButtonPress(ActionEvent event) {
        partCompanyNameField.setVisible(true);
        partCompanyNameLabel.setVisible(true);
        inHouseButton.setSelected(false);
        inHouse = false;
        outsourcedButton.setSelected(true);
        partMachineIDField.setVisible(false);
        partMachineIDLabel.setVisible(false);
    }

    @FXML
    /**
     * inHouse Radio Button to get the machineId field
     */
    void inHouseButtonAction(ActionEvent event) {
        partCompanyNameField.setVisible(false);
        partCompanyNameLabel.setVisible(false);
        outsourcedButton.setSelected(false);
        inHouse = true;
        inHouseButton.setSelected(true);
        partMachineIDField.setVisible(true);
        partMachineIDLabel.setVisible(true);
    }

    @FXML
    /**
     * save Button it will save the data and also will update the data accordingly
     */
    void saveButtonAction(ActionEvent event) {
        try {
            id = Integer.parseInt(partIDTextField.getText());
            partName = partNameTextField.getText();
            inv = Integer.parseInt(partLnvField.getText());
            price = Double.parseDouble(partPriceField.getText());
            max = Integer.parseInt(partMaxField.getText());
            min = Integer.parseInt(partMinField.getText());
        } catch (NumberFormatException e) {
            nameLabel.setVisible(true);
            nameLabel1.setVisible(true);
            nameLabel2.setVisible(true);
        } catch (RuntimeException e) {

        } catch (Exception e) {

        }

        if (partNameTextField.getText().isEmpty() || partNameTextField.getText() == "") {
            nameLabel3.setVisible(true);
        } else if (max > min) {
            if (inv < max) {
                String companyNameOrMachineID;
                if (inHouse) {
                    companyNameOrMachineID = partMachineIDField.getText();
                } else {
                    companyNameOrMachineID = partCompanyNameField.getText();
                }
                if (editData == true) {

                    inventoryController.updatePart(id, partName, inv,
                            price, modifyPart, max, min,
                            companyNameOrMachineID, inHouse, asProID
                    );
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Successfully Updated The Existing Data!");
                    alert.setContentText(null);
                    alert.showAndWait();
                } else {
                    inventoryController.addNewPart(
                            Integer.parseInt(partIDTextField.getText()), partNameTextField.getText(), Integer.parseInt(partLnvField.getText()),
                            Double.parseDouble(partPriceField.getText()), Integer.parseInt(partMaxField.getText()), Integer.parseInt(partMinField.getText()), companyNameOrMachineID, inHouse, asProID
                    );
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Successfully Added The Part!");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Max should not be less than Inventory Level");
                alert.setContentText(null);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Max should not be less than Min");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    /**
     * default controller to change the control according to the currently available Form
     *
     * @param documentController
     */
    void setParentController(Inventory documentController) {
        this.inventoryController = documentController;
    }

    /**
     * This method will set the modifyPart to the textFields
     *
     * @param modifyPart
     */
    void setData(Part modifyPart) {
        this.modifyPart = modifyPart;
        partIDTextField.setText(String.valueOf(modifyPart.getPartsID()));
        partNameTextField.setText("" + modifyPart.getPartsName());
        partLnvField.setText("" + modifyPart.getPartsLevel());
        partPriceField.setText("" + modifyPart.getPartsCost());
        editData = true;
        partMaxField.setText("" + modifyPart.getPartMax());
        partMinField.setText("" + modifyPart.getPartMin());
        inHouse = true;
        if (modifyPart.inHouse) {
            inHouseButtonAction(null);
            partMachineIDField.setText("" + modifyPart.getCompanyNameOrMachineID());      // setting machineid for inhouse parts
        } else {
            outsourceButtonPress(null);
            partCompanyNameField.setText("" + modifyPart.getCompanyNameOrMachineID());        // setting company name for outsourced parts
        }
    }

    /**
     * Auto generated Id
     *
     * @param generateID
     */
    void setID(int generateID) {
        partIDTextField.setText(String.valueOf(generateID));
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
