package orderData;

public class Order {

	int orderNumber;
	int visitorsNumber;
	String orderEmail;
	OrderType orderType;
	double price;
	int percent;
	double totalPrice;
	String parkname;
	String arrivedTime;
	String memberId=null;
	int ID=0;


	/**
	 * @param visitorsNumber
	 * @param orderEmail
	 * @param parkname
	 * @param arrivedTime
	 * @param memberId
	 * @param iD
	 */
	public Order(int visitorsNumber, String orderEmail, String parkname, String arrivedTime, String memberId,
			int iD) {
		super();
		this.visitorsNumber = visitorsNumber;
		this.orderEmail = orderEmail;
		this.parkname = parkname;
		this.arrivedTime = arrivedTime;
		this.memberId = memberId;
		ID = iD;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public String getParkname() {
		return parkname;
	}

	public void setParkname(String parkname) {
		this.parkname = parkname;
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

}
