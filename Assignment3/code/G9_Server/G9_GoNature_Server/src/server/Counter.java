package server;

import java.util.ArrayList;

//singleton counter
class Counter {
	private static Counter obj;
	private static int count;
	
	private Counter() {
		count = getLastNumber()+1;
	}

	public static Counter getCounter() {
		if (obj == null) {
			if (obj == null) {
				obj = new Counter();// instance will be created at request time
			}
		}
		return obj;
	}

	public int orderNum() {
		return count++;
	}
	
	private int getLastNumber() {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select");
		query.add("orders");
		query.add("orderNumber");
		query.add("ORDER BY orderNumber DESC LIMIT 1");
		query.add("1");
		ArrayList<ArrayList<String>> lastNum = MySQLConnection.select(query);
		return Integer.parseInt(lastNum.get(0).get(0));
	}
}
