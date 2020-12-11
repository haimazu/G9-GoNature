package sqlcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.*;
import dataLayer.*;

public class SQLCon {
	
	private static Connection dbConn = null;
	
	public static void main(String[] args) {
		ArrayList<String> strArr = new ArrayList<String>();
		strArr.add("localhost");
		strArr.add("3306");
		strArr.add("g9_gonature");
		strArr.add("root");
		strArr.add("NewP@ssword4theSQL");
		connectToDB(strArr);
		
		ArrayList<String> demo = new ArrayList<String>();
		demo.add("insert");
		demo.add("employee");
		demo.add("'bark','12345678','bar','katz','1','bar@Nature.com','ceo','jurasic'");
		
		ArrayList<String> demo2 = new ArrayList<String>();
		demo2.add("insert");
		demo2.add("orders");
		demo2.add("'222222','1','bar@Nature.com','single','150','jurasic','2021-01-01 08:00:00',null"); //YYYY-MM-DD hh:mm:ss
		
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

		
		if(demo.get(0).equals("insert"))
			insert(demo);
		if(demo2.get(0).equals("insert"))
			insert(demo2);
		if(demo3.get(0).equals("delete"))
			delete(demo3);
		if(demo4.get(0).equals("update"))
			update(demo4);
		if(demo5.get(0).equals("insert"))
			insert(demo5);
		if(demo6.get(0).equals("update"))
			update(demo6);

		
	}
	
	public static boolean insert(ArrayList<String> data) {
		String tableName = data.get(1);
		String values =  data.get(2);
		String StatmentString = ("INSERT into g9_gonature." + tableName + " values (" + values + ")");
		return execute(StatmentString,data);

	}
	
	public static boolean delete(ArrayList<String> data) {
		String tableName = data.get(1);
		String primaryKey = data.get(2);
		String pkValue = data.get(3);
		String StatmentString = ("DELETE FROM g9_gonature." + tableName +" WHERE (" + primaryKey + " = " + pkValue +")");
		return execute(StatmentString,data);
	}
	
	public static boolean select(ArrayList<String> data) {
		return true;
	}
	
	public static boolean update(ArrayList<String> data) {
		String tableName = data.get(1);
		String values = data.get(2); //need to look like: 'col1Name'='value1','col2Name'='value2'
		String primaryKey = data.get(3);
		String pkValue = data.get(4);
		String StatmentString = ("UPDATE g9_gonature."+ tableName + " SET " + values + " WHERE ("+ primaryKey + " = '" + pkValue +"')");
		System.out.println(StatmentString);
        return execute(StatmentString,data);
    }
	

	
	
	
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

//	public static String select(String[] fields, String selector) {
//
//		StringBuffer reply = new StringBuffer("<table border=1>");
//
//		String todo = ("SELECT * " + " from staff " + selector);
//
//		try {
//			java.sql.Statement s = conn.createStatement();
//			java.sql.ResultSet r = s.executeQuery(todo);
//			while (r.next()) {
//				reply.append("<tr>");
//				for (int i = 0; i < fields.length; i++) {
//					reply.append(tabit(r.getString(fields[i])));
//				}
//				reply.append("</tr>");
//			}
//			reply.append("</table>");
//		} catch (Exception e) {
//			return ("Oh oops - code 003\n" + e);
//		}
//
//		return (reply.toString());
//
//	}
//
//	private static String tabit(String box) {
//		return ("<td>" + box + "</td>");
//	}

	public static void connectToDB(ArrayList<String> str) {
		String dburl = "jdbc:mysql://" + str.get(0) + ":" + str.get(1) + "/" + str.get(2) + "?serverTimezone=IST";
		String username = str.get(3);
		String password = str.get(4);
		try {
			dbConn = DriverManager.getConnection(dburl, username, password);
		//	setMsg("DB " + str.get(2) + " Connected succsefuly on port " + str.get(1));
		//	DBup = true;
		} catch (SQLException e) {
			System.out.println("OH NO!");
		//	setMsg("could not connect to DB");
			e.printStackTrace();
		}
		System.out.println("Great Succsess! DB connected!");
	}
}
