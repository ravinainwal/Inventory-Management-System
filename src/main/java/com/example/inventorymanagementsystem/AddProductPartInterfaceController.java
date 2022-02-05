package com.example.inventorymanagementsystem;


import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AddProductPartInterfaceController implements Initializable {




    @FXML private TextField partCompanyNameField;
    @FXML private TextField partPriceField;
    @FXML private TextField partMaxField;
    @FXML private TextField partMinField;
    @FXML private TextField partNameTextField;
    @FXML private TextField partLnvField;
    @FXML private TextField partIDTextField=new TextField();
    @FXML private TextField partMachineIDField;
    @FXML private RadioButton inHouseButton,outsourcedButton;
    @FXML private Label partCompanyNameLabel,partMachineIDLabel;
    @FXML private Button closeButton;
    

    Part modifyPart;// Part object to modify the part
    Boolean editData = false;  //It is a flag to tell the button to save the part or update the part according the window in which we are working
    Boolean inHouse = true;//To check the part is inHouse or outhouse
    private AddProductFormController documentController;//FUTURE ENHANCEMENT and For changing the control and for
    int asProID = -1;//For the product and part association
    

    @FXML
    /**
     * Cancel Button for getting back to the main window
     */
    void cancelButtonAction(ActionEvent event){

            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

    }
    @FXML
    /**
     * This method is called according to the machineId and company name values
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
     * This method is called according to the machineId and company name values
     */
    void inHouseButtonAction(ActionEvent event){

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
     * update or save the part according to the flag
     */
    void partSaveButtonAction(ActionEvent event){

        if (Integer.parseInt(partMaxField.getText()) > Integer.parseInt(partMinField.getText())) {
            if (Integer.parseInt(partLnvField.getText()) < Integer.parseInt(partMaxField.getText())) {
                String companyNameOrMachineID;
                if (inHouse) {
                    companyNameOrMachineID = partMachineIDField.getText();
                } else {
                    companyNameOrMachineID = partCompanyNameField.getText();
                }
                if (editData == false) {

                    if(partNameTextField.getText()==""){
                        System.out.println("Name field is empty");
                    }

                    documentController.addToTempProductTableView(
                            Integer.parseInt(partIDTextField.getText()),
                            partNameTextField.getText(),
                            Integer.parseInt(partLnvField.getText()),
                            Double.parseDouble(partPriceField.getText()),
                            Integer.parseInt(partMaxField.getText()),
                            Integer.parseInt(partMinField.getText()),
                            companyNameOrMachineID,
                            inHouse,
                            asProID);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Successfully Added The Part!");
                    alert.setContentText(null);
                    alert.showAndWait();

                } else {

                    documentController.updatePart(
                            Integer.parseInt(partIDTextField.getText()),
                            partNameTextField.getText(),
                            Integer.parseInt(partLnvField.getText()),
                            Double.parseDouble(partPriceField.getText()),
                            modifyPart,
                            Integer.parseInt(partMaxField.getText()),
                            Integer.parseInt(partMinField.getText()),
                            companyNameOrMachineID,
                            inHouse,
                            asProID
                    );

                }

                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Max should not be less than inventory level!");
                alert.setContentText(null);
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Max should not be less than Min!");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    @Override
    /**
     * initialization
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    /**
     * setting control to different windows accordingly
     * @param documentController
     */
    void setParentController(AddProductFormController documentController) {
        this.documentController = documentController;
    }

    /**
     * to set the auto generated id
     * @param generateID
     */
    void setID(int generateID) {
        partIDTextField.setText(String.valueOf(generateID));
    }

    /**
     * to set the associated id
     * @param asProID
     */
    void setAssociatedPart(int asProID) {
        this.asProID = asProID;
    }





}
