package reportData;

import java.util.ArrayList;

import com.jfoenix.controls.JFXCheckBox;

import javafx.scene.control.CheckBox;

public class TableViewSet {
	
	private String reqType;
	private String reqDetails;
	private CheckBox mark;
	private String ParkName;
	/**
	 * @param parkName
	 * @param reqType
	 * @param reqDetails
	 * @param checkBox
	 */
	public TableViewSet(String parkName, String reqType, String reqDetails) {
		this.ParkName = parkName;
		this.reqType = reqType;
		this.reqDetails = reqDetails;
		this.mark = new CheckBox();
	}

	
	
	/**
	 * 
	 */
//	public TableViewSet(ArrayList<Object> arr) {
//		setParkName((String)arr.get(0));
//		setReqType((String) arr.get(1));
//		setReqDetails((String)arr.get(2));
//		setmark((CheckBox)arr.get(3));
//	}
	



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
	public CheckBox getmark() {
		return mark;
	}
	public void setmark(CheckBox checkBox) {
		this.mark = checkBox;
	}

	
	
	
}
