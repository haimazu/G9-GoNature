package reportData;

import java.util.ArrayList;

import com.jfoenix.controls.JFXCheckBox;

public class TableViewSet {
	
	
	String ParkName;
	String reqType;
	String reqDetails;
	JFXCheckBox checkBox;
	
	
	/**
	 * 
	 */
	public TableViewSet(ArrayList<Object> arr) {
		setParkName((String)arr.get(0));
		setReqType((String) arr.get(1));
		setReqDetails((String)arr.get(2));
		setCheckBox((JFXCheckBox)arr.get(3));
	}
	public String getParkName() {
		return ParkName;
	}
	public void setParkName(String parkName) {
		ParkName = parkName;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getReqDetails() {
		return reqDetails;
	}
	public void setReqDetails(String reqDetails) {
		this.reqDetails = reqDetails;
	}
	public JFXCheckBox getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(JFXCheckBox checkBox) {
		this.checkBox = checkBox;
	}

	
	
	
}
