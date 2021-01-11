package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;

public interface RportsInter {


	void VisitsReport(ArrayList<Object> recived, ConnectionToClient client);

}
