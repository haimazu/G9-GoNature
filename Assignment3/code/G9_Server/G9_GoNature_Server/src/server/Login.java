package server;

//import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;

public class Login {
	// input: ArrayList of objects ->
	//	in cell 0: the client function who called for service (in that case "login")
	//	in cell 1: ArrayList of String ->
	//					in cell 0: user name
	//					in cell 1: password
	//the function add the relevant data for the query and call MySQLConnection
	//output: none (send to client with ServerController)
	//send to client: ArrayList of objects ->
	//			in cell 0: the client function who called for service (in that case "login")
	//			in cell 1: ArrayList of String ->
	//							{if failed:} in cell 0: the string "failed"
	//							{if success:} in cell 0: the employee role as string
	@SuppressWarnings("unchecked")
	public static void login(ArrayList<Object> recived, ConnectionToClient client) {
		// add if db up later
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		ArrayList<String> data = (ArrayList<String>) recived.get(1);
		
		ArrayList<String> query = new ArrayList<String>();
		query.add("select");
		query.add("employee");
		query.add("role, firstName, parkName, loggedin");
		query.add("WHERE username='" + data.get(0) + "' AND password='" + data.get(1) + "'");
		query.add("4");
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		// return null or the user is already logged in
		if (queryData.isEmpty() || Integer.parseInt(queryData.get(0).get(3)) == 1) {
			answer.add("Failed");
		} else {
			answer.add(queryData.get(0));
		}
		EchoServer.sendToMyClient(answer, client);
	}

	// input: ArrayList of objects ->
	//	in cell 0: the client function who called for service (in that case "updateLoggedIn")
	//	in cell 1: ArrayList of String ->
	//					in cell 0: user name
	//					in cell 1: loggedin status
	//the function add the relevant data for the query and call MySQLConnection
	//output: none (send to client with ServerController)
	//send to client: ArrayList of objects ->
	//			in cell 0: the client function who called for service (in that case "updateLoggedIn")
	//			in cell 1: boolean value -> {if failed:} ==> false
	//									    {if success:} ==> true
	@SuppressWarnings("unchecked")
	public static void updateLoggedInStatus(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : updateLoggedIn
		answer.add(recived.get(0));
		// user loggedin status to update
		ArrayList<String> data = (ArrayList<String>) recived.get(1);

		ArrayList<String> query = new ArrayList<String>();
		query.add("update"); // command
		query.add("employee"); // table name
		query.add("loggedin = '" + data.get(1) + "'"); // columns to update
		query.add("username"); // condition
		query.add(data.get(0)); // username value

		answer.add(MySQLConnection.update(query));
		EchoServer.sendToMyClient(answer, client);
	}
}
