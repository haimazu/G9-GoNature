package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.*;
import dataLayer.*;

public class MySQLConnection {

	private static Connection dbConn = null;
	private static String dbScheme = null;

	public static void main(String[] args) { //main for test use only//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		ArrayList<String> strArrLst = new ArrayList<String>();
		strArrLst.add("localhost");
		strArrLst.add("3306");
		strArrLst.add("g9_gonature");
		strArrLst.add("root");
		//strArrLst.add("123456");
		strArrLst.add("NewP@ssword4theSQL");
		connectToDB(strArrLst);

		ArrayList<String> demo = new ArrayList<String>();
		demo.add("insert");
		demo.add("employee");
		demo.add("'bark','12345678','bar','katz','1','bar@Nature.com','ceo','jurasic'");

		ArrayList<String> demo2 = new ArrayList<String>();
		demo2.add("insert");
		demo2.add("orders");
		demo2.add("'222222','1','bar@Nature.com','single','150','jurasic','2021-01-01 08:00:00',null"); // YYYY-MM-DD
																										// hh:mm:ss

		ArrayList<String> demo3 = new ArrayList<String>();
		demo3.add("delete");
		demo3.add("orders");
		demo3.add("orderNumber");
		demo3.add("222222");

		ArrayList<String> demo4 = new ArrayList<String>();
		demo4.add("update");
		demo4.add("orders");
		demo4.add("visitorsNumber='10',arrivedTime='2021-01-02 10:00:00'");
		demo4.add("orderNumber");
		demo4.add("1111");

		ArrayList<String> demo5 = new ArrayList<String>();
		demo5.add("insert");
		demo5.add("member");
		demo5.add("'123456','rinat','students','1','972867648374','rinat@english.com','paypal','family','2'");

		ArrayList<String> demo6 = new ArrayList<String>();
		demo6.add("update");
		demo6.add("member");
		demo6.add("email='rinatbeeretz@haenglish.com',amount='3'");
		demo6.add("memberNumber");
		demo6.add("1");

		ArrayList<String> demo7 = new ArrayList<String>();
		demo7.add("select");
		demo7.add("member");
		demo7.add("*");
		demo7.add("");
		demo7.add("9");
		
		ArrayList<String> demo8 = new ArrayList<String>();
		demo8.add("select");
		demo8.add("member");
		demo8.add("ID,memberNumber");
		demo8.add("WHERE firstName = 'rinat'");
		demo8.add("2");
		
//		if (demo.get(0).equals("insert"))
//			insert(demo);
//		if (demo2.get(0).equals("insert"))
//			insert(demo2);
//		if (demo3.get(0).equals("delete"))
//			delete(demo3);
//		if (demo4.get(0).equals("update"))
//			update(demo4);
//		if (demo5.get(0).equals("insert"))
//			insert(demo5);
//		if (demo6.get(0).equals("update"))
//			update(demo6);
		if (demo7.get(0).equals("select"))
			System.out.println(select(demo7));
		if (demo7.get(0).equals("select"))
			System.out.println(select(demo8));
		disconnectFromDB();


	}
	
	//input: ArrayList of strings ->
	//		string in cell 0: server URL
	//		string in cell 1: server port (default for mySQL is 3306)
	//		string in cell 2: name of the DB scheme
	//		string in cell 3: user name
	//		string in cell 4: password
	//output: true if connection successful, false if failed
	public static boolean connectToDB(ArrayList<String> data) {
		String dburl = "jdbc:mysql://" + data.get(0) + ":" + data.get(1) + "/" + data.get(2) + "?serverTimezone=IST";
		dbScheme = data.get(2);
		String username = data.get(3);
		String password = data.get(4);
		try {
			dbConn = DriverManager.getConnection(dburl, username, password);
			// DBup = true;
		} catch (SQLException e) {
			System.out.println("OH NO!"); //remove after test
			e.printStackTrace();
			return false;
		}
		System.out.println("Great Succsess! DB connected!"); //remove after test
		return true;
	}

	//input: none
	//NOTE: make sure that you have an open connection before calling this method
	//output: true if connection closed successfully, false if failed
	public static boolean disconnectFromDB() {
		if (dbConn==null) {
			System.out.println("disconect failed: the DB is not even connected yet!"); //remove after test
			return false;
		} else {
			try {
				dbConn.close();			
				if(dbConn.isClosed()) {
					System.out.println("closed");//remove after test
					dbConn = null;
				}
			} catch (SQLException q) {
				q.printStackTrace();
				return false;
			} //catch (Exception e) {}  <--remove after test
			System.out.println("Disconected from DB succsefuly");//remove after test
			return true;
		}
	}

	//input: ArrayList of strings ->
	//		string in cell 0: command (in this case will always be "insert")
	//		string in cell 1: table name to insert to
	//		string in cell 2: values that should be inserted
	//NOTE: insert only full rows!
	//output: true if update successful, false if failed
	public static boolean insert(ArrayList<String> data) {
		String tableName = data.get(1);
		String values = data.get(2);
		String statmentString = ("INSERT into " + dbScheme + "." + tableName + " values (" + values + ")");
		return execute(statmentString, data);

	}

	//input: ArrayList of strings ->
	//		string in cell 0: command (in this case will always be "delete")
	//		string in cell 1: table name to delete from
	//		string in cell 2: primary key column name of that table
	//		string in cell 3: primary key value of the row that need to be deleted
	//NOTE: insert only full rows!
	//output: true if update successful, false if failed
	public static boolean delete(ArrayList<String> data) {
		String tableName = data.get(1);
		String primaryKey = data.get(2);
		String pkValue = data.get(3);
		String StatmentString = ("DELETE FROM " + dbScheme + "." + tableName + " WHERE (" + primaryKey + " = " + pkValue + ")");
		return execute(StatmentString, data);
	}

	//input: ArrayList of strings ->
	//		string in cell 0: command (in this case will always be "update")
	//		string in cell 1: table name to update data in
	//		string in cell 2: column names and value to put inside them (need to look like: col1Name='value1',col2Name='value2')
	//		string in cell 3: primary key column name of that table
	//		string in cell 4: primary key value of the row that need to be updated
	//NOTE: insert only full rows!
	//output: true if update successful, false if failed
	public static boolean update(ArrayList<String> data) {
		String tableName = data.get(1);
		String values = data.get(2); 
		String primaryKey = data.get(3);
		String pkValue = data.get(4);
		String StatmentString = ("UPDATE " + dbScheme + "." + tableName + " SET " + values + " WHERE (" + primaryKey + " = '"+ pkValue + "')");
		return execute(StatmentString, data);
	}

	//input: StatmentString ->
	//		string of the DB command to execute, it was build by either in 'update' 'delete' or 'insert' who call this method
	//		 ArrayList of strings ->
	//		string in cell 0: command (in this case will always be "update")
	//		string in cell 1: table name to delete from
	//NOTE: the data Arraylist may contain more than these two, but this is the only one we uses.
	//output: true if execution successful, false if failed
	private static boolean execute(String StatmentString, ArrayList<String> data) {
		String command = data.get(0);
		String tableName = data.get(1);
		int r;
		try {
			Statement s = dbConn.createStatement();
			r = s.executeUpdate(StatmentString);
		} catch (Exception e) {
			System.out.println("Oh no...\n" + e);
			return false;
		}
		System.out.println("executed " + command + " on table" + tableName + " (affected " + r + " rows)");
		return true;
	}

	//input: ArrayList of strings ->
	//		string in cell 0: command (in this case will always be "select")
	//		string in cell 1: table name to select from
	//		string in cell 2: columns that you want select to return (example: "*" for all, "ID,email" for those only two columns)
	//		string in cell 3: condition can contain a filter for a more refine search (example: "WHERE ID='1234'", "" if you dont want a filter)
	//		string in cell 4: the number of columns that you want select to return (important! provide a string of that number! i.e "7")
	//NOTE: even if you don't want to filter you will need to provide an empty string on cell 3
	//output: in case of success returns ArrayList that contains Arraylist of Strings ->
	//		each ArrayList of string represent a row in the DB replayed
	//		all the rows are packed together in an ArrayList that contains arraylist of strings
	//		in case of failure returns null
	public static ArrayList<ArrayList<String>> select(ArrayList<String> data) {
		String tableName = data.get(1);
		String columns = data.get(2);
		String condition = data.get(3);
		int replyColNum = Integer.parseInt(data.get(4));
		ArrayList<ArrayList<String>> reply = new ArrayList<ArrayList<String>>();
		String StatmentString = ("SELECT " + columns + " from "+ dbScheme +"." + tableName + " " + condition);
		try {
			ResultSet rs = dbConn.createStatement().executeQuery(StatmentString);
			while (rs.next()) {
				ArrayList<String> row = new ArrayList<String>();
				for (int i=1; i<replyColNum+1 ;i++) {
					row.add(rs.getString(i));
				}
				reply.add(row);
			}
		} catch (Exception e) {
			System.out.println("Oh no...\n" + e);
			return null;
		}
		return reply;
	}
	
	public static Connection getDbConn() {
		return dbConn;
	}
}
