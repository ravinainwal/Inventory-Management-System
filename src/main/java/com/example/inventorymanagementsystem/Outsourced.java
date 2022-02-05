package com.example.inventorymanagementsystem;

public class Outsourced extends Part {

    private String companyName;


    public Outsourced(String companyName, int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName to set the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
