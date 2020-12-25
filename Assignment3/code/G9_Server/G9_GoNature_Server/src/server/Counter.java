package server;

import java.util.ArrayList;

//singleton counter
class Counter {
	private static Counter obj;
	private static int count;
	private static int memCount;
	private static int waitlistNum;
	private static int cancelledCount;

	private Counter() {
		count = getLastNumber("orders", "orderNumber") + 1;
		memCount = getLastNumber("member", "memberNumber") + 1;
		waitlistNum = getLastNumber("waitinglist", "waitlistID") + 1;
		cancelledCount = getLastNumber("canceledorders", "orderNumber") + 1;
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

	public int memberNum() {
		return memCount++;
	}

	public int waitlistNum() {
		return waitlistNum++;
	}

	public int cancelledCountNum() {
		return cancelledCount++;
	}

	private int getLastNumber(String tableName, String Col) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select");
		query.add(tableName);
		query.add(Col);
		query.add("ORDER BY " + Col + " DESC LIMIT 1");
		query.add("1");
		ArrayList<ArrayList<String>> lastNum = MySQLConnection.select(query);
		return Integer.parseInt(lastNum.get(0).get(0));
	}
}
