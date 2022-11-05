package com.company;

import java.util.Scanner;
import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;
import java.lang.*;


public class GarageSystem 
{

    public enum ActiveSlotConfig
    {
        firstComeFirstServed, bestFit;

        public String toString()
        {
            switch (this)
            {
                case firstComeFirstServed: return "firstComeFirstServed";
                case bestFit: return "bestFit";
                default: return "";
            }
        }
    }

    private ActiveSlotConfig activeSlotConfig;
    private double totalIncome;
    private Garage garage;

    Scanner sc = new Scanner(System.in);

    public GarageSystem()
    {
        totalIncome = 0;
        garage = Garage.getInstance();
    }

    public void Setup() throws Exception
    {
        
        System.out.println("please set up the garage.. ");
        System.out.print("enter the maximum number of vehicles in the garage: ");
        int maxNumberOfVehicles = sc.nextInt();
        garage.setMaxNumberOfVehicles(maxNumberOfVehicles);


        System.out.print("enter the initial number of slots to set up the garage: ");
        int numberOfSlots = sc.nextInt();
        if (numberOfSlots > maxNumberOfVehicles)
            throw new Exception("Number of Slots is more than the max number of slots\nGarage not setup");
        garage.setNumberOfSlots(numberOfSlots);


        System.out.println("enter the active slot configuration algorithm:\n1: first come first served approach\n" +
                            "2: best fit approach");
        int choice = sc.nextInt();
        activeSlotConfig = ActiveSlotConfig.values()[choice-1];


        double width, depth;
        for(int i = 0; i<numberOfSlots; i++)
        {
            System.out.println("Enter Slot " + (i+1) + " width and depth:");
            width = sc.nextDouble();
            depth = sc.nextDouble();
            boolean x = addParkingSlot(width, depth);
        }

        System.out.println("Garage setup successfully!");
    }

    public boolean addParkingSlot(double width, double depth)
    {
        if (garage.getNumberOfSlots() >= garage.getMaxNumberOfVehicles())
        {
            System.out.println("No more space to add parking slots");
            return false;
        }

        Slot s = new Slot(width, depth);
        garage.addFreeSlot(s);
        garage.addSlot(s);
        return true;
    }

    public void parkIn()
    {
        System.out.println("starting park in");
        String modelName = "";
        int modelYear = 0;
        double width = 0, depth = 0;
      
        if(garage.getNumberOfVehicles() == garage.getMaxNumberOfVehicles())
        {
           System.out.println("max number of cars reached");
           return;
        }
        else
        {
            System.out.print("enter the model name: ");
            modelName = sc.next();
            System.out.print("enter the model year: ");
            modelYear = sc.nextInt();
            System.out.print("enter the vehicle's width: ");
            width = sc.nextDouble();
            System.out.print("enter the vehicle's depth: ");
            depth = sc.nextDouble();


            Vehicle vehicle = new Vehicle(modelName, modelYear, width, depth);

            assignSlot(activeSlotConfig, vehicle);

            System.out.println("Your slot ID is " + vehicle.getSlotID() + "\nYour vehicle ID is " + vehicle.getID());

            vehicle.setStartTime(System.nanoTime());

            garage.addVehicle(vehicle);

        }
    }

    public void assignSlot(ActiveSlotConfig slotConfig, Vehicle vehicle)
    {
        if (slotConfig == ActiveSlotConfig.firstComeFirstServed)
        {
            // first come algorithm
            firstComeFirstServed(vehicle);
        }
        else
        {
            // best fit algorithm
            bestFit(vehicle);
        }
        
    }

    public void firstComeFirstServed(Vehicle vehicle)
    {   
        Vector<Slot> freeSlots =  garage.getFreeSlots();

        for (int i = 0; i < freeSlots.size(); i++)
        {
            Slot freeSlot = freeSlots.get(i);
            if(freeSlot.getArea() >= vehicle.getArea())
            {
                vehicle.setSlotID(freeSlot.getSlotID());
                garage.getFreeSlots().get(i).markTaken();
                garage.addUsedSlot(freeSlot);
                garage.removeFreeSlot(i);
                return;
            }
        }
        System.out.println("No suitable slot available for your vehicle");
    }

    public void bestFit(Vehicle vehicle)
    {
        Vector<Slot> freeSlots = garage.getFreeSlots();
        Collections.sort(freeSlots, Comparator.comparing(s -> s.getArea()));
        double area = vehicle.getArea();


        for(int i = 0; i < freeSlots.size(); i++)
        {
            Slot freeSlot = freeSlots.get(i);
            if(freeSlot.getArea() >= area)
            {
                vehicle.setSlotID(freeSlot.getSlotID());
                garage.getFreeSlots().get(i).markTaken();
                garage.addUsedSlot(freeSlot);
                garage.removeFreeSlot(i);
                return;
            }
        }
        System.out.println("No suitable slot available for your vehicle");
    }

    public void parkOut()
    {
        System.out.println("starting park out");
        System.out.print("enter your vehicle's ID: ");
        int vehicleID = sc.nextInt(), slotID = checkVehicle(vehicleID);
        double time;
        Vector<Vehicle> vehicles = garage.getVehicles();
        Vector<Slot> usedSlots = garage.getUsedSlots();
        if(slotID != -1)
        {
            for(int i = 0; i < usedSlots.size(); i++)
            {
                Slot s = usedSlots.get(i);
                if(slotID == s.getSlotID())
                {
                    garage.markFree(i);
                    garage.addFreeSlot(s);
                    garage.removeUsedSlot(i);
                    break;
                }
            }

            for(int i = 0; i < vehicles.size(); i++)
            {
                Vehicle v = vehicles.get(i);
                if(vehicleID == v.getID())
                {
                    garage.getVehicles().get(i).setEndTime(System.nanoTime());
                    time = garage.getVehicles().get(i).getEndTime() - garage.getVehicles().get(i).getStartTime();
                    checkout(time);
                    garage.removeVehicle(i);
                    break;
                }
            }
        }
        else
            System.out.println("your vehicle does not exist in our garage");
    }

    public int checkVehicle(int id)
    {
        Vector<Vehicle> vehicles = garage.getVehicles();
        for(Vehicle v:vehicles)
        {
            if(v.getID() == id)
                return v.getSlotID();
        }
        return -1;
    }

    public void checkout(double time)
    {
        time /= 1000000000; time /= 3600;
        double cost = 5 * time;
        totalIncome += cost;
        System.out.println("Your parking fees are " + cost + " EGP");
    }

    public void display()
    {
        System.out.println("choose an option:\n1: display all slots.\n2: display free slots.\n" +
                         "3: display used slots.\n4: display parked vehicles.\n" +
                         "choose -1 to exit..");

        int choice;
        do
        {
            choice = sc.nextInt();
            switch (choice)
            {
                case 1: displayAllSlots();
                break;
                case 2: displayFreeSlots();
                break;
                case 3: displayUsedSlots();
                break;
                case 4: displayParkedVehicles();
            }
        }
        while(choice != -1);
    }

    private void displayAllSlots()
    {
        Vector<Slot> aSlot = garage.getSlots();

        for (int i = 0; i < aSlot.size(); i++)
        {
            System.out.println("Slot " + (i+1) + " info:");
            System.out.println("Slot ID: " + aSlot.get(i).getSlotID());
            System.out.println("Slot area: " + aSlot.get(i).getArea());
            System.out.println("Free: " + aSlot.get(i).isAvailable());
        }
    }

    private void displayFreeSlots()
    {
        Vector<Slot> fSlot = garage.getFreeSlots();

        for (int i = 0; i < fSlot.size(); i++)
        {
            System.out.println("Slot " + (i+1) + " info:");
            System.out.print("Slot ID: " + fSlot.get(i).getSlotID() +"\n");
            System.out.print("Slot area: " + fSlot.get(i).getArea() +"\n");
        }
    }

    private void displayUsedSlots()
    {
        Vector<Slot> uSlot = garage.getUsedSlots();
        

        for (int i = 0; i < uSlot.size(); i++)
        {
            System.out.println("Slot " + (i+1) + " info:");
            System.out.print("Slot ID: " + uSlot.get(i).getSlotID() + "\n");
            System.out.print("Slot area: " + uSlot.get(i).getArea() +"\n");
        }
    }

    private void displayParkedVehicles()
    {
        Vector<Vehicle> pVehicles = garage.getVehicles();

        for (int i = 0; i < pVehicles.size(); i++)
        {
            System.out.println("Vehicle parking slot ID: " + pVehicles.get(i).getSlotID());
            System.out.println("Vehicle area: " + pVehicles.get(i).getArea());
        }
    }

    public void calculateTotalIncome()
    {
        System.out.print("Total Number of vehicles parked in the garage until now is: " + garage.getTotalNumberOfVehicles() +
                         "\nTotal income is: " + totalIncome);
    }
}


