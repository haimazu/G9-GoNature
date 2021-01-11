package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;

public interface ISQL {

	ArrayList<ArrayList<String>> SelectWrap(ArrayList<String> query);

}
