package com.example.inventorymanagementsystem;


/**
 *
 * @author place your name here
 */
public class Product {
    
    private int id;
    private String name;
    private int stock;
    private double price;
    private int max;
    private int min;
    
    public Product(int productID, String productName, int productLevel, double productCost, int productMax, int productMin){
        this.id = productID;
        this.name = productName;
        this.stock = productLevel;
        this.price = productCost;
        this.max = productMax;
        this.min = productMin;
    }

    /**
     *
      * @return max
     */
    public int getProductMax() {
        return max;
    }

    /**
     *
     * @param productMax set max
     */
    public void setProductMax(int productMax) {
        this.max = productMax;
    }

    /**
     *
     * @return min
     */
    public int getProductMin() {
        return min;
    }

    /**
     *
     * @param productMin set min
     */
    public void setProductMin(int productMin) {
        this.min = productMin;
    }

    /**
     *
     * @return id
     */
    public int getProductID() {
        return id;
    }

    /**
     *
     * @param productID set id
     */
    public void setProductID(int productID) {
        this.id = productID;
    }

    /**
     *
     * @return name
     */
    public String getProductName() {
        return name;
    }

    /**
     * set name
     * @param productName
     */
    public void setProductName(String productName) {
        this.name = productName;
    }

    /**
     *
      * @return stock
     */
    public int getProductLevel() {
        return stock;
    }

    /**
     *
     * @param productLevel set stock
     */
    public void setProductLevel(int productLevel) {
        this.stock = productLevel;
    }

    /**
     *
     * @return price
     */
    public double getProductCost() {
        return price;
    }

    /**
     *
     * @param productCost set price
     */
    public void setProductCost(double productCost) {
        this.price = productCost;
    }
    
}
