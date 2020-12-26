package server;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import ocsf.server.ConnectionToClient;

public class Reports {

	// Overall Visitors Report generated by visitors type
	public static void OverallVisitorsReport(ArrayList<Object> recived, ConnectionToClient client) {

	}

	// When The Park Wasn't Full
	public static void UsageReport(ArrayList<Object> recived, ConnectionToClient client) {

	}

	// generated by visitors type
	public static void VisitsReport(ArrayList<Object> recived, ConnectionToClient client) {

	}

	// input: ArrayList<Object>,ConnectionToClient
	// Cancellation Report and visits lost
	// output: list of cancelled orders
	// public static void CancellationReport(ArrayList<Object> recived,
	// ConnectionToClient client) {
	public static void CancellationReport() {
		// Getting the current month
		int currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		System.out.println(currentmonth);
		int currentyear = Calendar.getInstance().get(Calendar.YEAR);
		System.out.println(currentyear);

		// the returned values stored here
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : ??
		// answer.add(recived.get(0));************
		// cell 0: the service name

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("canceledorders"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE MONTH(arrivedTime) = '" + currentmonth + "'"); // condition
		query.add("12"); // how many columns returned
		System.out.println(query.toString());
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		System.out.println(queryData.get(0));
		if (queryData.isEmpty()) {
			// no canceled orders in this month
			answer.add(new ArrayList<String>(Arrays.asList("Failed")));
		} else {
			ArrayList<ArrayList<String>> canceledOrders = new ArrayList<ArrayList<String>>();
			for (ArrayList<String> a : queryData) {
				ArrayList<String> oneOrderData = new ArrayList<String>();
				for (String b : a)
					oneOrderData.add(b);
			}
			answer.add(canceledOrders);
			for (ArrayList<String> a : canceledOrders) {
				for (String b : a)
					System.out.println(b + " ");
				System.out.println("\n");
			}
			// EchoServer.sendToMyClient(answer, client);
		}
	}

	public static void main(String[] args) {
		ArrayList<String> strArrLst = new ArrayList<String>();
		strArrLst.add("localhost");
		strArrLst.add("3306");
		strArrLst.add("g9_gonature");
		strArrLst.add("root");
		//strArrLst.add("123456");
		strArrLst.add("NewP@ssword4theSQL");
		MySQLConnection.connectToDB(strArrLst);
		CancellationReport();
	}

}
