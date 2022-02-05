package com.example.inventorymanagementsystem;


/**
 * @author place your name here
 */
public class Part {
    
    private int id;
    private String name;
    private int stock;
    private double price;
    private int max;
    private int min;
    private String companyNameOrMachineID;
    boolean inHouse = true;
    int associatedPartID = -1;

    public Part(int partsID, String partsName, int partsLevel, double partsCost, int partMax, int partMin, String companyNameOrMachineID, boolean inHouse, int associatedPartID) {
        
        this.id = partsID;
        this.name = partsName;
        this.stock = partsLevel;
        this.price = partsCost;
        this.max = partMax;
        this.min = partMin;
        this.companyNameOrMachineID = companyNameOrMachineID;
        this.inHouse = inHouse;
        this.associatedPartID = associatedPartID;
        
    }

    public Part(int id, String name, double price, int stock, int max, int min) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.max = max;
        this.min = min;
    }

    /**
     *
     * @return associatedPartID
     */
    public int getAssociatedPartID() {
        return associatedPartID;
    }

    /**
     *
     * @param associatedPartID set associatedPartID
     */
    public void setAssociatedPartID(int associatedPartID) {
        this.associatedPartID = associatedPartID;
    }

    /**
     *
     * @return inHouse
     */
    public boolean isInHouse() {
        return inHouse;
    }

    /**
     *
     * @param inHouse set inHouse
     */
    public void setInHouse(boolean inHouse) {
        this.inHouse = inHouse;
    }

    /**
     *
     * @return max
     */
    public int getPartMax() {
        return max;
    }

    /**
     *
      * @param partMax set max
     */
    public void setPartMax(int partMax) {
        this.max = partMax;
    }

    /**
     *
     * @return min
     */
    public int getPartMin() {
        return min;
    }

    /**
     *
     * @param partMin set min
     */
    public void setPartMin(int partMin) {
        this.min = partMin;
    }

    /**
     *
     * @return return company name or machine id
     */
    public String getCompanyNameOrMachineID() {
        return companyNameOrMachineID;
    }

    /**
     *
     * @param companyNameOrMachineID set company name or machine id
     */
    public void setCompanyNameOrMachineID(String companyNameOrMachineID) {
        this.companyNameOrMachineID = companyNameOrMachineID;
    }
    /**
     *
     * @return id
     */
    public int getPartsID() {
        return id;
    }

    /**
     *
     * @param partsID setid
     */
    public void setPartsID(int partsID) {
        this.id = partsID;
    }

    /**
     *
     * @return name
     */
    public String getPartsName() {
        return name;
    }

    /**
     *
     * @param partsName set name
     */
    public void setPartsName(String partsName) {
        this.name = partsName;
    }

    /**
     *
     * @return level
     */
    public int getPartsLevel() {
        return stock;
    }

    /**
     *
     * @param partsLevel set level
     */
    public void setPartsLevel(int partsLevel) {
        this.stock = partsLevel;
    }

    /**
     *
     * @return get price
     */
    public double getPartsCost() {
        return price;
    }

    /**
     *
     * @param partsCost set price
     */
    public void setPartsCost(double partsCost) {
        this.price = partsCost;
    }
    
    
    
}
