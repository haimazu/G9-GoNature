package sim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BarcodeSimulation {
	private static BarcodeSimulation obj;
	private static int choice;
	private static int orderNum;
	private static boolean listener;

	// SingletonBuilder
	private BarcodeSimulation() {
		listener = true;
		// on start up do this
	}

	public int giveItToMe() {
		listener = false;
		while (!listener)
			;
		return orderNum;
	}

	// Singleton, create one instance only
	public static BarcodeSimulation getSim() {
		if (obj == null) {
			if (obj == null) {
				obj = new BarcodeSimulation();// instance will be created at request time
			}
		}
		return obj;
	}

	public static void loop() throws NumberFormatException, IOException {
		listener = true;
		System.out.print("========= Simulation ============ \n");
		System.out.print("\nlistening for calls...\n");
		while (listener) {
			while (listener) {
			}
			do {
				System.out.print("========= Barcode Simulation ========== \n");
				System.out.print(" 1. Random Answer \n");
				System.out.print(" 2. Manual Answer \n");
				System.out.print(" 0. Exit \n");
				System.out.print("Enter your choice: ");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				choice = Integer.parseInt(br.readLine());
				switch (choice) {
				case 1: {
					orderNum = new SplittableRandom().nextInt(0, 1_001);
					listener = true;
					break;
				}
				case 2: {
					System.out.print("Enter your choice: ");
					br = new BufferedReader(new InputStreamReader(System.in));
					orderNum = Integer.parseInt(br.readLine());
					listener = true;
					break;
				}
				case 0: {
					System.out.println("see you soon!");
					orderNum = 0;
					System.out.print("\nlistening for calls...\n");
					listener = true;
					break;
				}
				default: {
					System.out.print("\nbad choice, not available\n");
					break;
				}
				}// end of switch

			} while (choice != 0 || choice != 1 || choice != 2);

		}
	}
	public static void main(String[] args) {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new Runnable() {
			@Override
			public void run() {
				try {
					getSim().loop();
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
	}
}
