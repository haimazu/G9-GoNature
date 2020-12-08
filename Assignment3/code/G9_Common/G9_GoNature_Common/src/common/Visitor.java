package common;

import java.util.ArrayList;

public class Visitor {
	private String firstName;
	private String lastName;
	private String ID;
	private String email;
	private String phoneNum;
	
	public Visitor(String firstName, String lastName, String ID, String email, String phoneNum){
		setFirstName(firstName);
		setLastName(lastName);
		setID(ID);
		setEmail(email);
		setPhoneNum(phoneNum);
	}
	
	public Visitor(ArrayList<String> al) {
		setFirstName(al.get(0));
		setLastName(al.get(1));
		setID(al.get(2));
		setEmail(al.get(3));
		setPhoneNum(al.get(4));
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}
	
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
}
