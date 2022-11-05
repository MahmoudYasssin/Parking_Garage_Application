package com.company;

public class Vehicle {

    // attributes
    private String modelName;
    private int ID;
    static private int counter = 1;
    private int modelYear;
    private int slotID;
    private double width;
    private double depth;
    private double startTime;
    private double endTime;
    
    public Vehicle(String modelName, int modelYear, double width, double depth) {
        this.modelName = modelName;
        ID = counter;
        this.modelYear = modelYear;
        this.width = width;
        this.depth = depth;
        counter++;
        slotID = -1;
    }

    public void setStartTime(double startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(double endTime)
    {
        this.endTime = endTime;
    }

    public double getStartTime()
    {
        return startTime;
    }

    public double getEndTime()
    {
        return endTime;
    }

    public void setSlotID(int slotID)
    {
        this.slotID = slotID;
    }

    public int getSlotID()
    {
        return slotID;
    }

    public int getID()
    {
        return ID;
    }

    public double getArea()
    {
        return width * depth;
    }
}
