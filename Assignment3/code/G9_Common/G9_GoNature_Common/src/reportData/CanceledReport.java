package reportData;

import java.io.Serializable;
import java.util.ArrayList;

import orderData.OrderType;

@SuppressWarnings("serial")
public class CanceledReport implements Serializable {
	private int visitorsNumber;
	private String email;
	private String phoneNumber;
	private OrderType orderType;
	private double price;
	private double totalPrice;
	private String parkName;
	private String arrivedTime;
	private String memberId;
	private String Id;
	private int amountArrived;
	private int orderNumber;

	public CanceledReport(ArrayList<String> cancelReportFromDB) {
		this.visitorsNumber = Integer.parseInt(cancelReportFromDB.get(0));
		this.email = cancelReportFromDB.get(1);
		this.phoneNumber = cancelReportFromDB.get(2);
		this.orderType = OrderType.valueOf(cancelReportFromDB.get(3).toUpperCase());
		this.price = Double.parseDouble(cancelReportFromDB.get(4));
		this.totalPrice = Double.parseDouble(cancelReportFromDB.get(5));
		this.parkName = cancelReportFromDB.get(6);
		this.arrivedTime = cancelReportFromDB.get(7);
		this.memberId = cancelReportFromDB.get(8);
		this.Id = cancelReportFromDB.get(9);
		this.amountArrived = Integer.parseInt(cancelReportFromDB.get(10));
		this.orderNumber = Integer.parseInt(cancelReportFromDB.get(11));
	}

	public int getVisitorsNumber() {
		return visitorsNumber;
	}

	public void setVisitorsNumber(int visitorsNumber) {
		this.visitorsNumber = visitorsNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public int getAmountArrived() {
		return amountArrived;
	}

	public void setAmountArrived(int amountArrived) {
		this.amountArrived = amountArrived;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

}
