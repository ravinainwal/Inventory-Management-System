package com.example.inventorymanagementsystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Inventory implements Initializable {

    ObservableList<Part> parts = FXCollections.observableArrayList();
    ObservableList<Product> products = FXCollections.observableArrayList();
    @FXML
    TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productID;
    @FXML
    private TableColumn<Product, Integer> productLevel;
    @FXML
    private TableColumn<Product, Double> productCost;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TextField productFilterString;
    @FXML
    TableView<Part>  partsTable;
    @FXML
    private TableColumn<Part, Integer> partsID;
    @FXML
    private TableColumn<Part, Integer> partsLevel;
    @FXML
    private TableColumn<Part, Double> partsCost;
    @FXML
    private TableColumn<Part, String> partsName;
    @FXML
    private TextField partsFilterString;
    

    Part modifyPart = null;//"FUTURE ENHANCEMENT" and To save the part for modification
    Product modifyProduct = null;//"FUTURE ENHANCEMENT" and To save the product for modification
    SortedList<Part> sortedData;//"FUTURE ENHANCEMENT" and for search operations
    AddProductFormController afc;

    public ObservableList<Part> getParts() {
        return parts;
    }

    public void setParts(ObservableList<Part> parts) {
        this.parts = parts;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productTable.getItems().setAll(products());
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productLevel.setCellValueFactory(new PropertyValueFactory<>("productLevel"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("productCost"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));

        productTable.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                
                modifyProduct = productTable.getSelectionModel().getSelectedItem();
                
            }
        });
        
        partsTable.getItems().setAll(parts());
        partsID.setCellValueFactory(new PropertyValueFactory<>("partsID"));
        partsLevel.setCellValueFactory(new PropertyValueFactory<>("partsLevel"));
        partsCost.setCellValueFactory(new PropertyValueFactory<>("partsCost"));
        partsName.setCellValueFactory(new PropertyValueFactory<>("partsName"));

        partsTable.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                
                modifyPart = partsTable.getSelectionModel().getSelectedItem();
                
            }
        });
        
    }

    @FXML
    /**
     * To open the add window for parts
     */
    void partAddButtonAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPartForm.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Part Window");
        stage.setScene(new Scene(root));
        loader.<AddPartFormController>getController().setParentController(this);
        AddPartFormController api = loader.getController();
        api.setID(generatePartsID());
        stage.show();
    }
    
    @FXML
    /**
     * To open the part modify form
     */
    void partsModifyButtonAction(ActionEvent event) throws IOException {
        
        if (modifyPart != null) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPartForm.fxml"));
                Parent root = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setTitle("Modify Part Window");
                stage.setScene(new Scene(root));
                loader.<AddPartFormController>getController().setParentController(this);

                AddPartFormController api = loader.getController();
                api.setData(modifyPart);
                stage.show();
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select Part To Modify");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }
    
    @FXML
    /**
     * To delete any part if it is not associated with any product otherwise it will show a warning
     */
    void deletePartsAction(ActionEvent event) {
        
        if (modifyPart != null && modifyPart.getAssociatedPartID() == -1) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("This Will Delete The Part Having Part ID : " + modifyPart.getPartsID() + "?");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { 
                    partsTable.getItems().remove(modifyPart);
                    parts.remove(modifyPart);
            }
        }
        else if (modifyPart != null && modifyPart.getAssociatedPartID() != -1) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("Selected part is associated with a Product Having Product ID : " + modifyPart.associatedPartID + "and can not be deleted please go to the modify part section.");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductForm.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Update Product Window");
                    stage.setScene(new Scene(root));
                    loader.<AddProductFormController>getController().setParentController(this);
                    for (Product p : products) {
                        if (modifyPart.associatedPartID == p.getProductID()) {
                            modifyProduct = p;
                        }
                    }
                    AddProductFormController api = loader.getController();
                    api.setData(modifyProduct);

                    for (Part p : parts) {
                        if (modifyProduct.getProductID() == p.getAssociatedPartID()) {
                            api.existingProductParts.add(p);
                        }
                    }
                    api.partsTable.getItems().setAll(api.existingProductParts);
                    stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(" At least one part should be selected!");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }
    
    @FXML
    /**
     * To search a part
     */
    void partsSearchButtonAction(ActionEvent event) {
        FilteredList<Part> filteredData = new FilteredList<>(parts,p -> true);
        
                    filteredData.setPredicate(Part -> {
                            if (partsFilterString.getText() == null || partsFilterString.getText().isEmpty()) {
                                return true;
                            }

                            String lowerCaseFilter = partsFilterString.getText().toLowerCase();

                            if(Part.getPartsName().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } 
                            
                            return false;
                        });
               

        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(partsTable.comparatorProperty());
        partsTable.setItems(sortedData);

    }
    
    @FXML
    /**
     * To open the product add form
     */
    void productAddButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("AddProductForm.fxml"));
        Parent root1 = loader1.load();
        Stage stage1 = new Stage();
        stage1.setTitle("Add Product Window");
        stage1.setScene(new Scene(root1));
        loader1.<AddProductFormController>getController().setParentController(this);
        AddProductFormController api = loader1.getController();
        api.setData(generateProductsID());
        stage1.show();
        
    }

    @FXML
    /**
     * To exit the program
     */
    void exitButtonAction(ActionEvent event){
        System.exit(0);
    }
    
    @FXML
    /**
     * To open the modify Product window
     */
    void productModifyButtonAction(ActionEvent event) throws IOException {
        
        if (modifyProduct != null) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductForm.fxml"));


            Parent root =  loader.load();
                Stage stage = new Stage();
                stage.setTitle("Update Product Window");

                stage.setScene(new Scene(root));
                loader.<AddProductFormController>getController()
                        .setParentController(this);

                AddProductFormController api = loader.getController();
                api.setData(modifyProduct);

                for (Part p : parts) {
                    if (modifyProduct.getProductID() == p.getAssociatedPartID()) {
                        api.existingProductParts.add(p);
                    }
                }
                api.partsTable.getItems().setAll(api.existingProductParts);
                stage.show();
        }else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("At least one part should be selected!");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    /**
     * Auto generate the Part ID
     * @return
     */
    public int generatePartsID(){
        int a = 1;
        
        for( Part o: parts){
            if(o.getPartsID() >= a){
                a = o.getPartsID() + 1;
            }
        }
        
        return a;
    }
    /**
     * Auto generate the Product ID
     * @return
     */
    int generateProductsID(){
        int a = 1;
        
        for( Product o: products){
            if(o.getProductID() >= a){
                a = o.getProductID() + 1;
            }
        }
        
        return a;
    }
    
    @FXML
    /**
     * To delete the product
     */
    void deleteProductSelected(ActionEvent event) {
        
        if (modifyProduct != null) {
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("Do you really want to delete the part having Part ID : " + modifyProduct.getProductID() + "?");
            String warningText = "Deletion of this product will lead to the associated parts deletion:";
            
            for( Part p: parts){
                if(p.getAssociatedPartID() == modifyProduct.getProductID()){
                    warningText += "\nProduct ID : "+p.getPartsID();
                }
            }
            alert.setContentText(warningText);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                for (Iterator<Part> p1 = parts.iterator(); p1.hasNext();) {
                    Part p = p1.next();
                    if (p.getAssociatedPartID() == modifyProduct.getProductID()) {
                        
                        p1.remove();
                        partsTable.getItems().remove(p);
                        
                    }
                }
                
                productTable.getItems().remove(modifyProduct);
                products.remove(modifyProduct);
                
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Success!");
                alert1.setHeaderText("Deleted the product with all the associated parts!");
                alert1.setContentText(null);
                alert1.showAndWait();
            }
        }else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please, Select at-least one part to perform delete operation!");
            alert.setContentText(null);
            alert.showAndWait();
        }
        
    }
    
    @FXML
    /**
     * To search the products
     */
    void productSearchButtonAction(ActionEvent event) {
        FilteredList<Product> filteredData = new FilteredList<>(products,p -> true);
        
                    filteredData.setPredicate(Product -> {
                            if (productFilterString.getText() == null || productFilterString.getText().isEmpty()) {
                                return true;
                            }
                            String lowerCaseFilter = productFilterString.getText().toLowerCase();
                            if(Product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } 
                            
                            return false;
                        });
               

        SortedList<Product> sortedData1 = new SortedList<>(filteredData);
        sortedData1.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData1);

    }

    /**
     * Initial parts
     * @return
     */
    public ObservableList<Part> parts(){
        
        parts.add(new Part(1, "Part 1", 2, 71.78, 7, 3, "1", true, -1));
        parts.add(new Part(2, "Part 2", 3, 42.89, 10, 1, "Apple", false, 3));
        parts.add(new Part(3, "Part 3", 5, 63.87, 5, 1, "Samsung", false, 4));
        parts.add(new Part(4, "Part 4", 6, 54.78, 8, 2, "EA-Sports", false, 4));
        parts.add(new Part(5, "Part 5", 7, 78.89, 10, 1, "MainGear", false, 2));
        parts.add(new Part(6, "Part 6", 8, 73.87, 5, 1, "Intel", false, 1));
        parts.add(new Part(7, "Part 7", 5, 44.78, 8, 2, "AMD", false, 1));
        
        return parts;
        
    }

    /**
     * Initial Products
     * @return
     */
    public ObservableList<Product> products(){
        
        products.add(new Product(1, "Product 1", 2, 1.78, 12, 2));
        products.add(new Product(2, "Product 2", 3, 2.89, 12, 2));
        products.add(new Product(3, "Product 3", 1, 3.87, 12, 2));
        products.add(new Product(4, "Product 4", 4, 4.78, 12, 1));
        return products;
        
    }

    /**
     * to add the part to observable list
     */
    public void addNewPart(int pID, String pName, int pLevel, double pCost, int pMax, int pMin, String pCompMac, Boolean inHouse, int asID){
        parts.add(new Part( pID, pName, pLevel, pCost, pMax, pMin, pCompMac, inHouse, asID));
        partsTable.getItems().clear();
        partsTable.getItems().setAll(parts);
        }

    /**
     * to add the product to observable list
     */
    public void addNewProduct(int pID, String pName, int pLevel, double pCost, int pMax, int pMin){
        // adds new products to arraylist 
        products.add(new Product( pID, pName, pLevel, pCost, pMax, pMin));
        productTable.getItems().clear();
        productTable.getItems().setAll(products);
    }

    /**
     * to Update the part to observable list
     */
    void updatePart(int pID, String pName, int pLevel, double pCost, Part selectedPart, int pMax, int pMin, String pCompMach, Boolean inHouse, int asID) {
        selectedPart.setPartsCost(pID);
        selectedPart.setPartsName(pName);
        selectedPart.setPartsLevel(pLevel);
        selectedPart.setPartsCost(pCost);
        selectedPart.setPartMax(pMax);
        selectedPart.setPartMin(pMin);
        selectedPart.setCompanyNameOrMachineID(pCompMach);
        selectedPart.setInHouse(inHouse);
        partsTable.getItems().clear();
        partsTable.getItems().setAll(parts);   
        
    }

    /**
     * to Update the Product to observable list
     */
    void updateProduct(int pID, String pName, int pLevel, double pCost, Product productSelected, int pMax, int pMin) {
        productSelected.setProductID(pID);
        productSelected.setProductName(pName);
        productSelected.setProductCost(pCost);
        productSelected.setProductLevel(pLevel);
        productSelected.setProductMax(pMax);
        productSelected.setProductMin(pMin);
        productTable.getItems().clear();
        productTable.getItems().setAll(products);
    }

}
