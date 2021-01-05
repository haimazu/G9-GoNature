package server;

import java.util.ArrayList;

/**
 * The Counter program generates unique numbers to insert in DB as primary keys
 *
 * @author Roi Amar, Anastasia Kokin
 * 
 */

class Counter {
	private static Counter obj;
	private static int count;
	private static int memCount;
	private static int waitlistNum;
	private static int cancelledCount;
	private static int discountsIDcount;

	private Counter() {
		count = getLastNumber("orders", "orderNumber") + 1;
		memCount = getLastNumber("member", "memberNumber") + 1;
		waitlistNum = getLastNumber("waitinglist", "waitlistID") + 1;
		cancelledCount = getLastNumber("canceledorders", "orderNumber") + 1;
		discountsIDcount = getLastNumber("discounts", "requestID") + 1;
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

	public int discountsIDcountNum() {
		return discountsIDcount++;
	}

	/**
	 * Finds the number that symbols the last unique number in DB 
	 * 
	 * @param String tableName, String Column 
	 * @return Integer 
	 */
	private int getLastNumber(String tableName, String Col) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select");
		query.add(tableName);
		query.add(Col);
		query.add("ORDER BY " + Col + " DESC LIMIT 1");
		query.add("1");
		ArrayList<ArrayList<String>> lastNum = MySQLConnection.select(query);
		if (lastNum.isEmpty())
			return 1111;
		System.out.println("counter: " + lastNum.get(0).get(0));
		return Integer.parseInt(lastNum.get(0).get(0));
	}
}
