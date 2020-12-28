package server;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import ocsf.server.ConnectionToClient;
import orderData.Order;

public class Reports {

	// input: ArrayList<Object>: cell[0] name
	// cell[1] month
	// cell[2] year ,ConnectionToClient
	// Overall Visitors Report generated by visitors type. by parks with option for
	// all parks
	// output: for every day in the dates range: amount for every type
	public static void OverallVisitorsReport(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : ??
		answer.add(recived.get(0));
		int month = (int) recived.get(1);
		int year = (int) recived.get(2);
		int day = 31;
		int currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (month == currentmonth)
			day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("orders"); // table name
		query.add("*"); // columns to select from

		query.add("WHERE MONTH(arrivedTime) = '" + month + "' AND YEAR(arrivedTime) = '" + year
				+ "' AND DAY(arrivedTime)<= '" + day + "' AND amountArrived='0'"); // condition
		query.add("12"); // how many columns returned

	}

	// When The Park Wasn't Full
	// input: array list of objects contains:
	// [0] -> String "UsageReport"
	// [1] -> Array list of String contains:
	// [0] -> String parkName (if all parks should be "all")
	// [1] -> String startDate in a following date format: (YYYY-MM-DD)
	// [2] -> String endDate in a following date format: (YYYY-MM-DD)
	// output: NONE
	// send to client: Array list that contains:
	// [0-n] -> Array list of string that contains: (n depends on number of entrys
	// in the DB)
	// [0] -> date and time of a capsule that was not full
	// [1] -> Difference between max allowed and actual arrived
	public static void UsageReport(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<String> dataFromClient = (ArrayList<String>) recived.get(1);
		ArrayList<Object> answer = new ArrayList<Object>();
		ArrayList<String> notFullDaysRow = new ArrayList<String>();
		ArrayList<ArrayList<String>> notFullDaysTable = new ArrayList<ArrayList<String>>();
		answer.add(recived.get(0));
		String parkName = dataFromClient.get(0);//// insert all for all parks
		String parkCond = " ";
		if (!parkName.equals("all"))
			parkCond = "parkName='" + parkName + "' AND ";
		String startDate = dataFromClient.get(1);
		String endDate = dataFromClient.get(2);
		String dateCond = "arrivedTime BETWEEN '" + startDate + "' AND '" + endDate + "'";
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // select
		query.add("orders"); // tableName
		query.add("arrivedTime,SUM(amountArrived) AS amountArrived"); // columns
		query.add("WHERE " + parkCond + dateCond + " GROUP BY arrivedTime ORDER BY arrivedTime"); // condition
		query.add("2"); // replyColNum
		ArrayList<ArrayList<String>> parkSummedCapacityByCapsule = MySQLConnection.select(query);
		query.clear();
		query.add("select"); // select
		query.add("park"); // tableName
		query.add("maxVisitorsAmount"); // columns
		if (parkName.equals("all"))
			query.add("WHERE " + dateCond); // condition
		else
			query.add("WHERE parkName='" + parkName + "' AND " + dateCond); // condition
		query.add("1"); // replyColNum
		ArrayList<ArrayList<String>> maxCapacityForPark = MySQLConnection.select(query);
		int maxCapacity = Integer.parseInt(maxCapacityForPark.get(0).get(0));
		for (ArrayList<String> row : parkSummedCapacityByCapsule) {
			double capacityInCapsule = Double.parseDouble(row.get(1));
			if (capacityInCapsule < maxCapacity) {
				notFullDaysRow.add(row.get(0));
				notFullDaysRow.add(String.valueOf(maxCapacity - capacityInCapsule));
				notFullDaysTable.add(notFullDaysRow);
				notFullDaysRow.clear();
			}
		}
		answer.add(notFullDaysRow);
		try {
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// generated by visitors type
	// by time.
	public static void VisitsReport(ArrayList<Object> recived, ConnectionToClient client) {

	}

	// input: ArrayList<Object>: cell[0] name
	// cell[1] month
	// cell[2] year ,ConnectionToClient
	// Cancellation Report and visits lost
	// output: list of cancelled orders:
	// ArrayList<Object>: cell[0] func_name
	// cell[1] list of cancelled orders
	// cell[2] list of dismissed orders
	public static void CancellationReport(ArrayList<Object> recived, ConnectionToClient client) {
		// the returned values stored here
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : getCancellationReports
		// cell 0: the service name
		answer.add(recived.get(0));

		ArrayList<String> dataFromClient = (ArrayList<String>) recived.get(1);
		String startDate = dataFromClient.get(0);
		String endDate = dataFromClient.get(1);
		String dateCond = "arrivedTime BETWEEN '" + startDate + "' AND '" + endDate + "'";

		// pre-cancelled orders
		ArrayList<String> query1 = new ArrayList<String>();
		query1.add("select"); // command
		query1.add("canceledorders"); // table name
		query1.add("*"); // columns to select from
		query1.add("WHERE " + dateCond); // condition
		query1.add("12"); // how many columns returned
		System.out.println(query1.toString());
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query1);
		if (queryData.isEmpty()) {
			// no canceled orders in this month
			answer.add(new ArrayList<String>(Arrays.asList("Failed")));
			EchoServer.sendToMyClient(answer, client);
			return;
		} else {
			answer.add(queryData);
			System.out.println(queryData);// **
		}

		// Dismmised orders
		ArrayList<String> query2 = new ArrayList<String>();
		query2.add("select"); // command
		query2.add("orders"); // table name
		query2.add("*"); // columns to select from
		query2.add("WHERE " + dateCond + " AND visitorsNumber != amountArrived"); // condition
		query2.add("12"); // how many columns returned
		System.out.println(query2.toString());
		ArrayList<ArrayList<String>> queryData2 = MySQLConnection.select(query2);
		if (queryData2.isEmpty()) {
			// no canceled orders in this month
			answer.add(new ArrayList<String>(Arrays.asList("Failed")));
			EchoServer.sendToMyClient(answer, client);
			return;
		} else {
			answer.add(queryData2);
			System.out.println(queryData2);
			EchoServer.sendToMyClient(answer, client);
		}

	}

	public static void main(String[] args) {
		ArrayList<String> strArrLst = new ArrayList<String>();
		strArrLst.add("localhost");
		strArrLst.add("3306");
		strArrLst.add("g9_gonature");
		strArrLst.add("root");
		// strArrLst.add("123456");
		strArrLst.add("Aa123456");
		MySQLConnection.connectToDB(strArrLst);
		// CancellationReport(12, 2020);
	}

}
