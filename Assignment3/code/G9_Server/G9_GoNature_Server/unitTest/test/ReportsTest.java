package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javafx.beans.binding.When;
import ocsf.server.ConnectionToClient;
import server.ISQL;
import server.MySQLConnection;
import server.Reports;

class ReportsTest {

//	private ArrayList<ArrayList<String>> value;
//	private ISQL check;
//
//	private ArrayList<ArrayList<String>> acctualResult;
//	private ArrayList<ArrayList<String>> expectedResult;
//
//	public static class StubwrapSQL implements ISQL {
//
//		@Override
//		public ArrayList<ArrayList<String>> SelectWrap(ArrayList<String> query) {
//
//			return value;
//		}
//
//	}

//	@BeforeEach
//	void setUp() throws Exception {
//		value = new ArrayList<ArrayList<String>>();
//		check = new StubwrapSQL();
//		report = new Reports();
//
//		acctualResult = new ArrayList<ArrayList<String>>();
//		expectedResult = new ArrayList<ArrayList<String>>();
//	}

	MySQLConnection msqlconnection = Mockito.mock(MySQLConnection.class);
	public static ConnectionToClient client;
	private Reports report;

	@Test
	void test() {
		
		report = new Reports();
		ArrayList<String> query= new ArrayList<String>(); 
		
		query.add("select");
		query.add("enteryandexit");
		query.add("SUM(amountArrived)");
		query.add("WHERE orderType='member' AND timeEnter BETWEEN '2020-12-01' AND '2020-12-10'"
				+"AND HOUR(TIME(timeExit))- HOUR(TIME(timeEnter)) <=1 "
				+ " AND HOUR(TIME(timeExit)) - HOUR(TIME(timeEnter)) > 0");
		query.add("1");
		
		
		
		ArrayList<Object> a=new ArrayList<Object>();
		a.add("getRegularsVisitorsData");
		a.add("2020-12-01");
		a.add("2020-12-10");
		a.add("member");
		Reports.VisitsReport(a, client);
		
		when(msqlconnection.select(query)).thenReturn(new ArrayList<Object>());
		
		verify(msqlconnection,Times(4)).select(query);
		}
}

//	@Test
//	void test() {
//		report.VisitsReport(recived, client);
//		
//		value.add(new ArrayList<String>{"getRegularsVisitorsData",  "1.6129032258064515", "11.29032258064516", "56.451612903225815", "30.64516129032258"});
//		report.VisitsReport(new ArraList<Object>{"getRegularsVisitorsData","2020-12-01","2020-12-10","member" }, client);
//		
//	}

}
