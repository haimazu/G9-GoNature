package dataLayer;

import java.util.ArrayList;

/**
 * The TableCurrentVisitors contain current visitors for a park
 *
 * @author Bar Katz
 */

public class TableCurrentVisitors {
	
	private String ParkNameVis;
	private String CurrentAmount;
	private String MaxVisitors;
	
	
	/**
	 * constructor for TableCurrentVisitors
	 * @param parkName String
	 * @param currentAmount String
	 */
	
	public TableCurrentVisitors(String parkName, String currentAmount) {
		super();
		this.ParkNameVis = parkName;
		this.CurrentAmount = currentAmount;
	}
	

	public String getParkNameVis() {
		System.out.println("got park");
		return ParkNameVis;
	}
	public void setParkNameVis(String parkName) {
		System.out.println("set park");
		ParkNameVis = parkName;
	}
	public String getCurrentAmount() {
		System.out.println("got vis");
		return CurrentAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		System.out.println("set vis");
		CurrentAmount = currentAmount;
	}

	public String getMaxVisitors() {
		return MaxVisitors;
	}


	public void setMaxVisitors(String maxVisitors) {
		MaxVisitors = maxVisitors;
	}
	
}
