package reportData;

import java.util.ArrayList;

import orderData.DateAndTime;

public class Report {
	
	private int totalVisitorCapacity;
	private DateAndTime startDate;
	private DateAndTime endDate;
	private DateAndTime notFullyBooked;
	private double calcRevenueForPark;
	private int cancleOrder;
	private ReportType reportType;
	
	
	/**
	 * @param totalVisitorCapacity
	 * @param startDate
	 * @param endDate
	 * @param notFullyBooked
	 * @param calcRevenueForPark
	 * @param cancleOrder
	 * @param reportType
	 */
	public Report(int totalVisitorCapacity, DateAndTime startDate, DateAndTime endDate, DateAndTime notFullyBooked,
			double calcRevenueForPark, int cancleOrder, ReportType reportType) {
		super();
		this.totalVisitorCapacity = totalVisitorCapacity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.notFullyBooked = notFullyBooked;
		this.calcRevenueForPark = calcRevenueForPark;
		this.cancleOrder = cancleOrder;
		this.reportType = reportType;
	}
	
	public int getTotalVisitorCapacity() {
		return totalVisitorCapacity;
	}
	public void setTotalVisitorCapacity(int totalVisitorCapacity) {
		this.totalVisitorCapacity = totalVisitorCapacity;
	}
	public DateAndTime getStartDate() {
		return startDate;
	}
	public void setStartDate(DateAndTime startDate) {
		this.startDate = startDate;
	}
	public DateAndTime getEndDate() {
		return endDate;
	}
	public void setEndDate(DateAndTime endDate) {
		this.endDate = endDate;
	}
	public DateAndTime getNotFullyBooked() {
		return notFullyBooked;
	}
	public void setNotFullyBooked(DateAndTime notFullyBooked) {
		this.notFullyBooked = notFullyBooked;
	}
	public double getCalcRevenueForPark() {
		return calcRevenueForPark;
	}
	public void setCalcRevenueForPark(double calcRevenueForPark) {
		this.calcRevenueForPark = calcRevenueForPark;
	}
	public int getCancleOrder() {
		return cancleOrder;
	}
	public void setCancleOrder(int cancleOrder) {
		this.cancleOrder = cancleOrder;
	}
	public ReportType getReportType() {
		return reportType;
	}
	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}
}
