package com.company;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

	    GarageSystem garageSystem = new GarageSystem();

	    garageSystem.Setup();

		int choice;
		do
		{
			System.out.println("choose an option:\n1: park in.\n2: park out.\n" +
					"3: display.\n4: calculate total income.\n" +
					"choose -1 to exit..");

			choice = sc.nextInt();

			switch (choice)
			{
				case 1: garageSystem.parkIn();
					break;
				case 2: garageSystem.parkOut();
					break;
				case 3: garageSystem.display();
					break;
				case 4: garageSystem.calculateTotalIncome();
			}
		}
		while(choice != -1);
    }
}
