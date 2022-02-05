package com.example.inventorymanagementsystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddProductFormController implements Initializable {
    @FXML
    private TextField  productPriceField;
    @FXML
    private TextField  productMaxField;
    @FXML
    private TextField  productMinField;
    @FXML
    private TextField  productNameTextField;
    @FXML
    private TextField productInvField;
    @FXML
    private TextField  productIDTextField;
    @FXML
    TableView<Part>  partsTable;
    @FXML
    TableView<Part> partsTable1;
    @FXML
    private TableColumn<Part, Integer> partsID;
    @FXML
    private TableColumn<Part, Integer>partsLevel;
    @FXML
    private TableColumn<Part, Integer>partsID1;
    @FXML
    private TableColumn<Part, Integer>partsLevel1;
    @FXML
    private TableColumn<Part, Double> partsCost;
    @FXML
    private TableColumn<Part, Double> partsCost1;
    @FXML
    private TableColumn<Part, String> partsName;
    @FXML
    private TableColumn<Part, String> partsName1;
    @FXML
    private TextField productFilterString;
    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private Label nameLabel2;

    @FXML
    private Label nameLabel3;
    int id;
    String productName;
    int inv;
    Double price;
    int max;
    int min;


    private Inventory documentController=new Inventory();
    private SortedList<Part> sortedData;

    Product modifyProduct;//to save the product which we will modify
    Part modifyPart;//to save the part which we will modify
    Boolean editData = false;//It is a flag to accordingly change the functionality of save and update button
    ObservableList<Part> productParts = FXCollections.observableArrayList();
    ObservableList<Part> existingProductParts = FXCollections.observableArrayList();
    AddProductPartInterfaceController api=new AddProductPartInterfaceController();

    ObservableList<Part> allPart = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        allPart.addAll(documentController.parts());
        partsTable1.getItems().setAll(allPart);
        partsID.setCellValueFactory(new PropertyValueFactory<>("partsID"));
        partsLevel.setCellValueFactory(new PropertyValueFactory<>("partsLevel"));
        partsCost.setCellValueFactory(new PropertyValueFactory<>("partsCost"));
        partsName.setCellValueFactory(new PropertyValueFactory<>("partsName"));


        partsTable1.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                modifyPart = partsTable1.getSelectionModel().getSelectedItem();

            }
        });

        partsID1.setCellValueFactory(new PropertyValueFactory<>("partsID"));
        partsLevel1.setCellValueFactory(new PropertyValueFactory<>("partsLevel"));
        partsCost1.setCellValueFactory(new PropertyValueFactory<>("partsCost"));
        partsName1.setCellValueFactory(new PropertyValueFactory<>("partsName"));

        partsTable.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                modifyPart = partsTable.getSelectionModel().getSelectedItem();
            }
        });
    }

    @FXML
    /**
     * to remove the associated parts
     */
    void removeAssociatedPart(ActionEvent event) {
        if (modifyPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Remove part which has partID : " + modifyPart.getPartsID() + " From this Product?");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                partsTable.getItems().remove(modifyPart);
                productParts.remove(modifyPart);
                modifyPart.setAssociatedPartID(-1);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select at-least one part to perform delete operation!");
            alert.setContentText(null);
            alert.showAndWait();
        }

    }


    @FXML
    /**
     * cancel button it will switch the current window with main
     */
    void cancelButtonAction(ActionEvent event){
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
    }

    @FXML
    /**
     * This button will associate a part with a product
     */
    private void addProductAssociatedPart(ActionEvent event) throws IOException {

        if(modifyPart!=null){
            modifyPart.setAssociatedPartID(Integer.parseInt(productIDTextField.getText()));
            productParts.add(modifyPart);
            partsTable.getItems().clear();
            partsTable.getItems().setAll(productParts);
            documentController.addNewPart(modifyPart.getPartsID(), modifyPart.getPartsName(), modifyPart.getPartsLevel(),
                    modifyPart.getPartsCost(), modifyPart.getPartMax(), modifyPart.getPartMin(), modifyPart.getCompanyNameOrMachineID(),
                    modifyPart.isInHouse(), modifyPart.getAssociatedPartID());
            existingProductParts.add(modifyPart);


        }
    }

    @FXML
    /**
     * This button is used To save a product and update a product
     */
    void productSaveButtonAction(ActionEvent event){

        try{
            id=Integer.parseInt(productIDTextField.getText());
            productName =productNameTextField.getText();
            inv=Integer.parseInt(productInvField.getText());
            price= Double.parseDouble(productPriceField.getText());
            max=Integer.parseInt(productMaxField.getText());
            min=Integer.parseInt(productMinField.getText());
        }catch(NumberFormatException e){
            nameLabel.setVisible(true);
            nameLabel1.setVisible(true);
            nameLabel2.setVisible(true);
        } catch (RuntimeException e ){

        }catch (Exception e){

        }

        if(productNameTextField.getText().isEmpty()||productNameTextField.getText()==""){
            nameLabel3.setVisible(true);
        } else

        if(productParts.isEmpty() == false || existingProductParts.isEmpty() == false){
            if (max > min) {
                if (inv < max) {
                    if (editData == true) {
                        documentController.updateProduct(id, productName, inv,
                                price, modifyProduct, max, min);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success!");
                        alert.setHeaderText("Successfully Updated The Existing Product !");
                        alert.setContentText(null);
                        alert.showAndWait();
                    } else {
                        documentController.addNewProduct(
                                Integer.parseInt(productIDTextField.getText()), productNameTextField.getText(), Integer.parseInt(productInvField.getText()),
                                Double.parseDouble(productPriceField.getText()), Integer.parseInt(productMaxField.getText()), Integer.parseInt(productMinField.getText())
                        );
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success!");
                        alert.setHeaderText("Successfully Added The Product!");
                        alert.setContentText(null);
                        alert.showAndWait();
                    }
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Max Should Not Be Less Than Inventory Level!");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Max value can not be lower then min value!");
                alert.setContentText(null);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Products Should have at least one part associated with it!");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }


    /**
     * changing the control accordingly
     * @param documentController
     */
    void setParentController(Inventory documentController) {
        this.documentController = documentController;
    }

    /**
     * set data to the text fields to modify
     * @param productSelected
     */
    void setData(Product productSelected) {
        productIDTextField.setText(""+productSelected.getProductID());
        productNameTextField.setText(productSelected.getProductName());
        productInvField.setText("" + productSelected.getProductLevel());
        productPriceField.setText("" + productSelected.getProductCost());
        productMaxField.setText("" + productSelected.getProductMax());
        productMinField.setText("" + productSelected.getProductMin());
        this.modifyProduct = productSelected;
        editData = true;
    }

    /**
     * to set the product Id to id field
     * @param generateProductsID
     */
    void setData(int generateProductsID) {
        productIDTextField.setText(""+generateProductsID);
    }

    /**
     *to add part to the product
     */
    void addToTempProductTableView(int pID, String pName, int pLevel, double pCost, int pMax, int pMin, String pCompMac, Boolean inHouse, int asID){
        productParts.add(new Part( pID, pName, pLevel, pCost, pMax, pMin, pCompMac, inHouse, asID));
        partsTable1.getItems().clear();
        partsTable1.getItems().setAll(productParts);
        documentController.addNewPart(pID, pName, pLevel, pCost, pMax, pMin, pCompMac, inHouse, asID);
    }



    /**
     *update part info to modify Part
     */
    void updatePart(int pID, String pName, int pLevel, double pCost, Part selectedPart, int pMax, int pMin, String pCompMach, Boolean inHouse, int asID) {
        modifyPart.setPartsCost(pID);
        modifyPart.setPartsName(pName);
        modifyPart.setPartsLevel(pLevel);
        modifyPart.setPartsCost(pCost);
        modifyPart.setPartMax(pMax);
        modifyPart.setPartMin(pMin);
        modifyPart.setCompanyNameOrMachineID(pCompMach);
        modifyPart.setInHouse(inHouse);
        modifyPart.setAssociatedPartID(asID);
        partsTable.getItems().clear();
        partsTable.getItems().setAll(existingProductParts);
        documentController.updatePart(pID, pName, pLevel, pCost, selectedPart, pMax, pMin, pCompMach, inHouse, asID);
    }
    @FXML
    /**
     * To search a part
     */
    void partsSearchButtonAction(ActionEvent event) {
        FilteredList<Part> filteredData = new FilteredList<>(allPart, p -> true);

        filteredData.setPredicate(Part -> {
            if (productFilterString.getText() == null || productFilterString.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = productFilterString.getText().toLowerCase();

            if(Part.getPartsName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }

            return false;
        });

        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(partsTable1.comparatorProperty());

        partsTable1.setItems(sortedData);

    }



}
