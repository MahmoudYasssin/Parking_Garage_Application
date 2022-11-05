package com.company;

import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Garage 
{
    
     //attributes
     private static Garage garage;
     private int MaxNumberOfVehicles;
     private int NumberOfVehicles = 0;
     private int NumberOfSlots = 0;
     private int totalNumberOfVehicles = 0;
     private Vector<Slot> slots;
     private Vector<Slot> freeSlots;
     private Vector<Slot> usedSlots;
     private Vector<Vehicle> vehicles;

    private Garage() {
        slots = new Vector<Slot>();
        freeSlots = new Vector<Slot>();
        usedSlots = new Vector<Slot>();
        vehicles = new Vector<Vehicle>();
    }

    public static Garage getInstance() {
        if (garage==null)
            garage = new Garage();
        return garage;
    }

    public Vector<Slot> getSlots()
    {
        return slots;
    }

    public Vector<Slot> getFreeSlots()
    {
        return freeSlots;
    }

    public Vector<Slot> getUsedSlots()
    {
        return usedSlots;
    }

    public Vector<Vehicle> getVehicles()
    {
        return vehicles;
    }

    public int getNumberOfVehicles()
    {
        return NumberOfVehicles;
    }

    public void setMaxNumberOfVehicles(int num)
    {
        MaxNumberOfVehicles = num;
    }

    public int getMaxNumberOfVehicles()
    {
        return MaxNumberOfVehicles;
    }

    public void setNumberOfSlots(int num)
    {
        NumberOfSlots = num;
    }

    public int getNumberOfSlots()
    {
        return NumberOfSlots;
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
        NumberOfVehicles++;
        totalNumberOfVehicles++;
    }

    public void removeVehicle(int index) {
        vehicles.remove(index);
        NumberOfVehicles--;
    }

    public void addFreeSlot(Slot s)
    {
        freeSlots.add(s);
    }

    public void addUsedSlot(Slot s)
    {
        usedSlots.add(s);
    }

    public void addSlot(Slot s) {
        slots.add(s);
        NumberOfSlots++;
    }

    public void removeFreeSlot(int index)
    {
        freeSlots.remove(index);
    }

    public void removeUsedSlot(int index)
    {
        usedSlots.remove(index);
    }

    public void markTaken(int index)
    {
        freeSlots.get(index).markTaken();
    }

    public void markFree(int index)
    {
        usedSlots.get(index).markFree();
    }

    public int getTotalNumberOfVehicles()
    {
        return totalNumberOfVehicles;
    }
}


