package userDate;

import common.LogIn;
import common.Status;
import dataLayer.Park;

public class Employee {

	private String employeeFirstName;
	private String employeeLasttName;
	private String employeeUserName;
	private String employeePassword;
	private Status employeeStatus;
	private LogIn systemUser;
	private Park park;
	/**
	 * @param employeeFirstName
	 * @param employeeLasttName
	 * @param employeeUserName
	 * @param employeePassword
	 * @param employeeNumber
	 * @param employeeEmail
	 * @param employeeParkName
	 * @param employeeStatus
	 */
	public Employee(String employeeFirstName, String employeeLasttName, String employeeUserName,
			String employeePassword, int employeeNumber, String employeeEmail, String employeeParkName,
			Status employeeStatus) {
		super();
		this.employeeFirstName = employeeFirstName;
		this.employeeLasttName = employeeLasttName;
		this.employeeUserName = employeeUserName;
		this.employeePassword = employeePassword;
		this.employeeNumber = employeeNumber;
		this.employeeEmail = employeeEmail;
		this.employeeParkName = employeeParkName;
		this.employeeStatus = employeeStatus;
		setUserSystem(this.employeeUserName, this.employeePassword);
	}
	
	
	private int employeeNumber;
	private String employeeEmail;
	private String employeeParkName;
	public String getEmployeeUserName() {
		return employeeUserName;
	}
	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}
	public String getEmployeePassword() {
		return employeePassword;
	}
	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}
	public int getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getEmployeeParkName() {
		return employeeParkName;
	}
	public void setEmployeeParkName(String employeeParkName) {
		this.employeeParkName = employeeParkName;
	}
	public Status getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(Status employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public void setUserSystem(String userName, String password) {
		systemUser=new LogIn(this);
	}
	

	public LogIn getUserSystem(String userName, String password) {
		return systemUser;
	}
	
	public boolean enterToTheSystem(String userName, String password) {
		return systemUser.loginToGoNatuerSystem(userName, password);
	}
	
	public void exitToTheSystem(String userName, String password) {
		 systemUser.logoutToGoNatuerSystem();
	}
	
	public void setPark(String ParkName) {
		this.park=new Park(ParkName);
		
	}
	
}
