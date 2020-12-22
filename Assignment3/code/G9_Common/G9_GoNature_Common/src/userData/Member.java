package userData;

import java.util.ArrayList;

import orderData.OrderType;

public class Member {

	String memberID;
	String memberFirstName;
	String memberLastName;
	String memberNumber;
	String memberPhoneNumber;
	String memberEmail;
	String memberPaymentType;
	OrderType memberOrderType;
	String memberAmount;

	// constructor from DB
	public Member(ArrayList<String> memberFromDB) {
		this.memberID = memberFromDB.get(0);
		System.out.println("ID");
		this.memberFirstName = memberFromDB.get(1);
		System.out.println("FN");
		this.memberLastName = memberFromDB.get(2);
		System.out.println("LN");
		this.memberNumber = memberFromDB.get(3);
		System.out.println("mem num");
		this.memberPhoneNumber = memberFromDB.get(4);
		System.out.println("phone");
		this.memberEmail = memberFromDB.get(5);
		System.out.println("email");
		this.memberPaymentType = memberFromDB.get(6);
		System.out.println("membb");
		this.memberOrderType = OrderType.valueOf(memberFromDB.get(7).toUpperCase());
		System.out.println("membaf");
		this.memberAmount = memberFromDB.get(8);
		System.out.println(123);
	}

	@Override
	public String toString() {
		return "Member [memberID=" + memberID + ", memberFirstName=" + memberFirstName + ", memberLastName="
				+ memberLastName + ", memberNumber=" + memberNumber + ", memberPhoneNumber=" + memberPhoneNumber
				+ ", memberEmail=" + memberEmail + ", memberPaymentType=" + memberPaymentType + ", memberOrderType="
				+ memberOrderType + ", memberAmount=" + memberAmount + "]";
	}

	public String getMemberFirstName() {
		return memberFirstName;
	}

	public void setMemberFirstName(String memberFirstName) {
		this.memberFirstName = memberFirstName;
	}

	public String getMemberLastName() {
		return memberLastName;
	}

	public void setMemberLastName(String memberLastName) {
		this.memberLastName = memberLastName;
	}

	public OrderType getMemberOrderType() {
		return memberOrderType;
	}

	public void setMemberOrderType(OrderType memberOrderType) {
		this.memberOrderType = memberOrderType;
	}

	public String getMemberAmount() {
		return memberAmount;
	}

	public void setMemberAmount(String memberAmount) {
		this.memberAmount = memberAmount;
	}

	public Member(String memberID, String memberFirstNAme, String memberLastNAme, String memberNumber,
			String memberPhoneNumber, String memberEmail, String memberPaymentType, String memberAmount) {
		super();
		this.memberID = memberID;
		this.memberFirstName = memberFirstNAme;
		this.memberLastName = memberLastNAme;
		this.memberNumber = memberNumber;
		this.memberPhoneNumber = memberPhoneNumber;
		this.memberEmail = memberEmail;
		this.memberPaymentType = memberPaymentType;
		this.memberAmount = memberAmount;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getMemberFirstNAme() {
		return memberFirstName;
	}

	public void setMemberFirstNAme(String memberFirstNAme) {
		this.memberFirstName = memberFirstNAme;
	}

	public String getMemberLastNAme() {
		return memberLastName;
	}

	public void setMemberLastNAme(String memberLastNAme) {
		this.memberLastName = memberLastNAme;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getMemberPhoneNumber() {
		return memberPhoneNumber;
	}

	public void setMemberPhoneNumber(String memberPhoneNumber) {
		this.memberPhoneNumber = memberPhoneNumber;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberPaymentType() {
		return memberPaymentType;
	}

	public void setMemberPaymentType(String memberPaymentType) {
		this.memberPaymentType = memberPaymentType;
	}

}
