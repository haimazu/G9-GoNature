package common;

import userDate.Employee;

public class ParkWorker extends Employee {


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
	public ParkWorker(String employeeFirstName, String employeeLasttName, String employeeUserName,
			String employeePassword, int employeeNumber, String employeeEmail, String employeeParkName,
			Status employeeStatus) {
		super(employeeFirstName, employeeLasttName, employeeUserName, employeePassword, employeeNumber, employeeEmail,
				employeeParkName, employeeStatus);
	}
	
	
	/*
	public int checkAmountInThePark(Date date) {
		
	}
	*/
	
	public void orderForNonResrvation() {
		
	}
	

}
