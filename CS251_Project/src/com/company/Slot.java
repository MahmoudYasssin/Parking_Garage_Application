package com.company;

public class Slot {
    
    // attributes

    private double width;
    private double depth;
    private int ID;
    static private int counter = 1;
    private boolean free;

    // methods
    public Slot(double width, double depth) {
        ID = counter;
        this.width = width;
        this.depth = depth;
        free = true;
        counter++;
    }

    public boolean isAvailable()
    {
        return free;
    }

    public void markTaken()
    {
        free = false;
    }

    public void markFree()
    {
        free = true;
    }

    public int getSlotID()
    {
        return ID;
    }

    public double getArea()
    {
        return width*depth;
    }
}
