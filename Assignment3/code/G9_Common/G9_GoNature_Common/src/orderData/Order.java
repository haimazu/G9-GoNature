package orderData;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

	int visitorsNumber;
	String orderEmail;
	String orderPhone;
	OrderType orderType; 
	double price; //before discount
	double totalPrice; //after discount
	String parkName;
	String arrivedTime;
	String memberId;
	String ID;
	int amountArrived;
	int orderNumber;

	// this constructor is only for ParkEmployeeController from method next -DO NOT USE
		// IT!!!
	//server side updates : check if member,calculate price
	public Order(String parkName,String arrivedTime,String memberId,String ID, int amountArrived) {
		this.parkName=parkName;
		this.arrivedTime=arrivedTime;
		this.amountArrived=amountArrived;
		this.memberId=memberId;
		this.ID=ID;
		this.orderEmail=null;
		this.orderPhone=null;
	}
	
	// this constructor is only for OrderConroller from method next -DO NOT USE
	// IT!!!
	public Order(int visitorsNumber, String orderEmail, String orderPhone, String parkName, String arrivedTime,
			String memberId, String ID) {

		this.visitorsNumber = visitorsNumber;
		this.orderEmail = orderEmail;
		this.orderPhone = orderPhone;
		this.parkName = parkName;
		this.arrivedTime = arrivedTime;
		this.memberId = memberId;
		this.ID = ID;
	}

	public Order(int orderNumber, int visitorsNumber, String orderEmail, String orderPhone, OrderType orderType,
			double price, double totalPrice, String parkName, String arrivedTime, String memberId, String ID) {

		this.visitorsNumber = visitorsNumber;
		this.orderEmail = orderEmail;
		this.orderPhone = orderPhone;
		this.orderType = orderType;
		this.price = price;
		this.totalPrice = totalPrice;
		this.parkName = parkName;
		this.arrivedTime = arrivedTime;
		this.memberId = memberId;
		this.ID = ID;
		this.amountArrived = 0;
		this.orderNumber = orderNumber;
	}

	public Order(ArrayList<String> orderFromDB) {

		this.visitorsNumber = Integer.parseInt(orderFromDB.get(0));
		this.orderEmail = orderFromDB.get(1);
		this.orderPhone = orderFromDB.get(2);
		this.orderType = OrderType.valueOf(orderFromDB.get(3).toUpperCase());
		this.price = Double.parseDouble(orderFromDB.get(4));
		this.totalPrice = Double.parseDouble(orderFromDB.get(5));
		this.parkName = orderFromDB.get(6);
		this.arrivedTime = orderFromDB.get(7);
		this.memberId = orderFromDB.get(8);
		this.ID = orderFromDB.get(9);
		this.amountArrived = Integer.parseInt(orderFromDB.get(10));
		this.orderNumber = Integer.parseInt(orderFromDB.get(11));
	}

	public int getAmountArrived() {
		return amountArrived;
	}

	public void setAmountArrived(int amountArrived) {
		this.amountArrived = amountArrived;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getVisitorsNumber() {
		return visitorsNumber;
	}

	public void setVisitorsNumber(int visitorsNumber) {
		this.visitorsNumber = visitorsNumber;
	}

	public String getOrderEmail() {
		return orderEmail;
	}

	public void setOrderEmail(String orderEmail) {
		this.orderEmail = orderEmail;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getArrivedTime() {
		return arrivedTime;
	}

	public void setArrivedTime(String arrivedTime) {
		this.arrivedTime = arrivedTime;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", visitorsNumber=" + visitorsNumber + ", orderEmail=" + orderEmail
				+ ", orderPhone=" + orderPhone + ", orderType=" + orderType + ", totalPrice=" + totalPrice + ", price="
				+ price + ", parkName=" + parkName + ", arrivedTime=" + arrivedTime + ", memberId=" + memberId + ", ID="
				+ ID + ", amountArrived=" + amountArrived + "]";
	}

	public String toStringForDB() {
		return "'" + getOrderNumber() + "','" + getVisitorsNumber() + "','" + getOrderEmail() + "','" + getOrderPhone()
				+ "','" + getOrderType().toString().toLowerCase() + "','" + getPrice() + "','" + getParkName() + "','"
				+ getArrivedTime() + "','" + getMemberId() + "'" + getID() + "'";
	}
	
	//checks if the order is for occasional visitor
	public boolean isOccasional() {
		if(this.orderEmail==null&& this.orderPhone==null)
			return true;
		return false;
	}
}
