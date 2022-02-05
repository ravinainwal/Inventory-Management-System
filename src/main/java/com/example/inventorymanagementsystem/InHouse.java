package com.example.inventorymanagementsystem;

public class InHouse extends Part {

    private int machineId;

    public InHouse(int machineId, int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId to set the machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
