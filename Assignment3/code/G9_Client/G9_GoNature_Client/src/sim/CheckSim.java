package sim;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckSim {
	public static void main(String[] args) {
		System.out.println("Start");
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new Runnable() {
			@Override
			public void run() {
				try {
					BarcodeSimulation.getSim().loop();
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
		System.out.println(BarcodeSimulation.getSim().giveItToMe());
	}
}
