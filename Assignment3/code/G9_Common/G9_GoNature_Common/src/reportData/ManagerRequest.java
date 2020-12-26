package reportData;

import java.io.Serializable;

public class ManagerRequest implements Serializable{

	@Override
	public String toString() {
		return "ManagerRequest [employeeID=" + employeeID + ", requestType=" + requestType + ", maxCapacity="
				+ maxCapacity + ", ordersCapacity=" + ordersCapacity + ", discount=" + discount + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", parkName=" + parkName + "]";
	}

	int employeeID;
	String requestType; //{discount, max_c, max_o}
	int maxCapacity;
	int ordersCapacity;
	String discount;
	String fromDate;
	String toDate;
	String parkName;

	
	public ManagerRequest(int employeeID, String requesttype, int maxCapacity, int ordersCapacity, String discount,
			String fromDate, String toDate, String parkName) {
		this.employeeID = employeeID;
		this.requestType = requesttype;
		this.maxCapacity = maxCapacity;
		this.ordersCapacity = ordersCapacity;
		this.discount = discount;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.parkName = parkName;
	}
	
	

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getRequesttype() {
		return requestType;
	}

	public void setRequesttype(String requesttype) {
		this.requestType = requesttype;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getOrdersCapacity() {
		return ordersCapacity;
	}

	public void setOrdersCapacity(int ordersCapacity) {
		this.ordersCapacity = ordersCapacity;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

}
