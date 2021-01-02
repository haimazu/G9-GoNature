package sim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import dataLayer.EmailMessege;
import dataLayer.Messege;
import dataLayer.SmsMessege;
import orderData.Order;

public class barcodeSimulation {
	private static barcodeSimulation obj;
	private static int choice;
	private static int orderNum;
	private static boolean listener;

	// SingletonBuilder
	private barcodeSimulation() {
		listener = true;
		// on start up do this
	}
	
	public int giveItToMe() {
		listener = false;
		while (!listener);
		return orderNum;
	}

	// Singleton, create one instance only
	public static barcodeSimulation getSim() {
		if (obj == null) {
			if (obj == null) {
				obj = new barcodeSimulation();// instance will be created at request time
			}
		}
		return obj;
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.print("========= Simulation ============ \n");
		System.out.print("listening for calls...");
		while (listener);
		do {
			System.out.print("========= Barcode Simulation ========== \n");
			System.out.print(" 1. Random Answer \n");
			System.out.print(" 2. Manual Answer \n");
			System.out.print(" 0. Exit \n");
			System.out.print("Enter your choice: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			choice = Integer.parseInt(br.readLine());
			switch (choice) {
			case 1:

				break;
			case 2:
				break;
			case 0: {
				System.out.println("see you soon!");
			}
				break;
			default: {
				System.out.println("\nbad choice, not available\n");
				break;
			}
			}// end of switch

		} while (choice != 0);

	}
}
