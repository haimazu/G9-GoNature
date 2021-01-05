package dataLayer;

import java.util.ArrayList;


public class TableCurrentVisitors {
	
	private String ParkNameVis;
	private String CurrentAmount;
	private String MaxVisitors;
	



	/**
	 * @param parkName
	 * @param currentAmount
	 */
	public TableCurrentVisitors(String parkName, String currentAmount) {
		super();
		this.ParkNameVis = parkName;
		this.CurrentAmount = currentAmount;
	}
	

	public String getParkNameVis() {
		return ParkNameVis;
	}
	public void setParkNameVis(String parkName) {
		ParkNameVis = parkName;
	}
	public String getCurrentAmount() {
		return CurrentAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		CurrentAmount = currentAmount;
	}

	public String getMaxVisitors() {
		return MaxVisitors;
	}


	public void setMaxVisitors(String maxVisitors) {
		MaxVisitors = maxVisitors;
	}
	
}
