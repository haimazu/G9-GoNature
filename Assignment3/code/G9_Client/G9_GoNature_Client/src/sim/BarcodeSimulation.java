package sim;

import java.util.ArrayList;
import java.util.SplittableRandom;

public class BarcodeSimulation {
	private static BarcodeSimulation obj;
	private static ArrayList<Integer> pool;
	
	// SingletonBuilder
	private BarcodeSimulation() {
		pool.add(1133);
		pool.add(1132);
		pool.add(1121);
		pool.add(1111);
		pool.add(1234);
		pool.add(5555);
		pool.add(1128);
		pool.add(1145);
		pool.add(1155);
		pool.add(1165);
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
	
	public static String read(int key) {
		switch (key) {
		case 0:
			return "g"+String.valueOf(new SplittableRandom().nextInt(1_000, 5_001));
		case 1:
			return "m"+String.valueOf(new SplittableRandom().nextInt(1_000, 5_001));
		case 2:
			return String.valueOf(new SplittableRandom().nextInt(1_000_000, 3_999_000));
		case 4:
			return String.valueOf(nextIn());
		default:
			return null;
		}
	}
	
	private static Integer nextIn() {
		if (pool.isEmpty())
			return null;
		Integer ret = pool.get(0);
		pool.remove(ret);
		return ret;
	}
}