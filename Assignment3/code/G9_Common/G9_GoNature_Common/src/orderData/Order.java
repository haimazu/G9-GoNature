package orderData;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{

	int orderNumber;
	int visitorsNumber;
	String orderEmail;
	String orderPhone; 
	OrderType orderType;
	double price;
	String parkName;
	String arrivedTime;
	String memberId;
	String ID;

	//this constructor is only for OrderConroller from method next -DO NOT USE IT!!!
	public Order(int visitorsNumber,String orderEmail,String orderPhone,String parkName,String arrivedTime,String memberId,String ID) {
		//this.orderNumber = orderNumber;
		this.visitorsNumber = visitorsNumber;
		this.orderEmail = orderEmail;
		this.orderPhone = orderPhone;
		//this.orderType = orderType;
		//this.price = price;
		this.parkName = parkName;
		this.arrivedTime = arrivedTime;
		this.memberId = memberId;
		this.ID = ID;
	}
	
	public Order(int orderNumber, int visitorsNumber,String orderEmail,String orderPhone, OrderType orderType,double price,String parkName,String arrivedTime,String memberId,String ID) {
		this.orderNumber = orderNumber;
		this.visitorsNumber = visitorsNumber;
		this.orderEmail = orderEmail;
		this.orderPhone = orderPhone;
		this.orderType = orderType;
		this.price = price;
		this.parkName = parkName;
		this.arrivedTime = arrivedTime;
		this.memberId = memberId;
		this.ID = ID;
	}

	public Order(ArrayList<String> orderFromDB) {
		this.orderNumber = Integer.parseInt(orderFromDB.get(0));
		this.visitorsNumber = Integer.parseInt(orderFromDB.get(1));
		this.orderEmail = orderFromDB.get(2);
		this.orderPhone = orderFromDB.get(3);
		this.orderType = OrderType.valueOf(orderFromDB.get(4));
		this.price = Double.parseDouble(orderFromDB.get(5));
		this.parkName = orderFromDB.get(6);
		this.arrivedTime = orderFromDB.get(7);
		this.memberId = orderFromDB.get(8);
		this.ID = orderFromDB.get(9);
		// TODO Auto-generated constructor stub
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
	
	public String toStringForDB() {
		return "'" + getOrderNumber() + "','"
				+ getVisitorsNumber() + "','"
				+ getOrderEmail() + "','"
				+ getOrderPhone() + "','"
				+ getOrderType().toString().toLowerCase() + "','"
				+ getPrice() + "','"
				+ getParkName() + "','"
				+ getArrivedTime() + "','"
				+ getMemberId() + "'"
				+ getID() + "'";
	}
}
