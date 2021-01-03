package sim;

import java.util.ArrayList;
import java.util.SplittableRandom;

public class BarcodeSimulation {
	private static BarcodeSimulation obj;
	private static ArrayList<String> pool;
	
	// SingletonBuilder
	private BarcodeSimulation() {
		pool.add("g1133");
		pool.add("m1132");
		pool.add("123456789");
		pool.add("112233441");
		pool.add("g1111");
		pool.add("m4212");
		pool.add("987654321");
		pool.add("144331122");
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
			return nextIn();
		default:
			return null;
		}
	}
	
	private static String nextIn() {
		if (pool.isEmpty())
			return null;
		String ret = pool.get(0);
		pool.remove(ret);
		return ret;
	}
}