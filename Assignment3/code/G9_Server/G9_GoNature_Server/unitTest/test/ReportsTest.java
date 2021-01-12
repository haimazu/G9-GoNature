package test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ocsf.server.ConnectionToClient;
import server.MySQLConnection;
import server.Reports;

public class ReportsTest {

	private ArrayList<Object> acctualResult;
	public static ConnectionToClient client;

	@Before
	public void setUp() throws Exception {
		ArrayList<String> data = new ArrayList<String>(
				Arrays.asList("localhost", "3306", "g9_gonature", "root", "Aa123456"));
		MySQLConnection.connectToDB(data);

		acctualResult = new ArrayList<Object>();
	}

	@Test
	public void test1() {
		ArrayList<Object> recived = new ArrayList<Object>();
		recived.add("getRegularsVisitorsData");
		ArrayList<String> a = new ArrayList<String>();

		a.add("2020-12-01");
		a.add("2020-12-10");
		a.add("member");
		recived.add(a);

		acctualResult = Reports.getVisitsReportData(recived);
		assertEquals(5, acctualResult.size());
	}

	@Test
	public void test2() {
		ArrayList<Object> recived = new ArrayList<Object>();
		recived.add("getRegularsVisitorsData");
		ArrayList<String> a = new ArrayList<String>();

		a.add("2022-11-01");
		a.add("2022-11-10");
		a.add("member");
		recived.add(a);

		acctualResult = Reports.getVisitsReportData(recived);
		assertEquals(2, acctualResult.size());
	}

	@Test
	public void test3() {
		MySQLConnection.setDbConn(null);
		ArrayList<Object> recived = new ArrayList<Object>();
		recived.add("getRegularsVisitorsData");
		ArrayList<String> a = new ArrayList<String>();

		a.add("2020-12-01");
		a.add("2020-12-10");
		a.add("member");
		recived.add(a);

		acctualResult = Reports.getVisitsReportData(recived);
		assertEquals(null, acctualResult);
	}
}
