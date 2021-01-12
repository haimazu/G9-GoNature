package test;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import javafx.beans.binding.When;
import ocsf.server.ConnectionToClient;
import server.ISQL;
import server.MySQLConnection;
import server.Reports;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

//public class ReportsTest {
//
//	private ArrayList<ArrayList<String>> value;
//	private ISQL check;
//	private Reports report;
//	private ArrayList<ArrayList<String>> acctualResult;
//	private ArrayList<ArrayList<String>> expectedResult;
//	private ArrayList<Object> recived;
//	public static ConnectionToClient client;
//
//	public static class StubwrapSQL implements ISQL {
//
//		@Override
//		public ArrayList<ArrayList<String>> SelectWrap(ArrayList<String> query) {
//			ArrayList<ArrayList<String>> expectedResult = new ArrayList<ArrayList<String>>();
//			ArrayList<String> b = new ArrayList<String>();
//			b.add("1.6129032258064515");
//			b.add("11.29032258064516");
//			b.add("56.451612903225815");
//			b.add("30.64516129032258");
//			expectedResult.add(b);
//			return expectedResult;
//		}
//
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		recived = new ArrayList<Object>();
//		check = new StubwrapSQL();
//		report = new Reports();
//
//		acctualResult = new ArrayList<ArrayList<String>>();
//		expectedResult = new ArrayList<ArrayList<String>>();
//	}
//
//	@Test
//	public void test() {
//		//ArrayList<ArrayList<String>> expectedResult = new ArrayList<ArrayList<String>>();
//		ArrayList<String> b = new ArrayList<String>();
//		b.add("getRegularsVisitorsData");
//		b.add("1.6129032258064515");
//		b.add("11.29032258064516");
//		b.add("56.451612903225815");
//		b.add("30.64516129032258");
//		expectedResult.add(b);
//
//		
//
//		ArrayList<String> a = new ArrayList<String>();
//		recived.add("getRegularsVisitorsData");
//		a.add("2020-12-01");
//		a.add("2020-12-10");
//		a.add("member");
//		recived.add(a);
//		Reports.VisitsReport(recived, client);
//		assertEquals(expectedResult, Reports.test);
//	}
//}

public class ReportsTest {
	MySQLConnection msqlconnection = mock(MySQLConnection.class);

	@Test
	public void test1() {
	doNothing().when(msqlconnection);
	MySQLConnection.select(isA(ArrayList.class));
	MySQLConnection.select(any());
	}

//	public static ConnectionToClient client;
//	private Reports report;
//	private ArrayList<ArrayList<String>> expectedResult = new ArrayList<ArrayList<String>>();
//
//	@SuppressWarnings("static-access")
//	@Test
//	public void test() {
//
//		report = new Reports();
//		ArrayList<String> query = new ArrayList<String>();
//		//
//		query.add("select");
//		query.add("enteryandexit");
//		query.add("SUM(amountArrived)");
//		query.add("WHERE orderType='regular' AND  timeEnter BETWEEN  '2020-12-01' AND '2020-12-11'"
//				+ "				  AND HOUR(TIME(timeExit))- HOUR(TIME(timeEnter)) <= 2"
//				+ "				  AND HOUR(TIME(timeExit)) - HOUR(TIME(timeEnter)) > 1");
//		query.add("1");
//
//		ArrayList<Object> recieved = new ArrayList<Object>();
//		recieved.add("getRegularsVisitorsData");
//		ArrayList<String> a = new ArrayList<String>();
//
//		a.add("2020-12-01");
//		a.add("2020-12-10");
//		a.add("member");
//		recieved.add(a);
//
//		ArrayList<String> b = new ArrayList<String>();
//		b.add("getRegularsVisitorsData");
//		b.add("1.6129032258064515");
//		b.add("11.29032258064516");
//		b.add("56.451612903225815");
//		b.add("30.64516129032258");
//		expectedResult.add(b);
//		// when(msqlconnection.select(query).equals(null)).then(null);
//		// doNothing().when(msqlconnection.select(query));
//		when(MySQLConnection.select(any())).thenReturn(expectedResult);
//		doNothing().when(msqlconnection);
//		// when(MySQLConnection.select(query)).then((Answer<?>) new
//		// ArrayList<String>());
//
//		Reports.VisitsReport(recieved, client);
//
//		verify(msqlconnection, times(4));
//		MySQLConnection.select(any());
//	}
}
