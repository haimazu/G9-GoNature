package userData;

public class Member {
	

	String memberID;
	String memberFirstNAme;
	String memberLastNAme;
	int memberNumber;
	String memberPhoneNumber;
	String memberEmail;
	PaymentType memberPaymentType;
	
	
	/**
	 * @param memberID
	 * @param memberFirstNAme
	 * @param memberLastNAme
	 * @param memberNumber
	 * @param memberPhoneNumber
	 * @param memberEmail
	 * @param memberPaymentType
	 */
	public Member(String memberID, String memberFirstNAme, String memberLastNAme, int memberNumber,
			String memberPhoneNumber, String memberEmail, PaymentType memberPaymentType) {
		super();
		this.memberID = memberID;
		this.memberFirstNAme = memberFirstNAme;
		this.memberLastNAme = memberLastNAme;
		this.memberNumber = memberNumber;
		this.memberPhoneNumber = memberPhoneNumber;
		this.memberEmail = memberEmail;
		this.memberPaymentType = memberPaymentType;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getMemberFirstNAme() {
		return memberFirstNAme;
	}
	public void setMemberFirstNAme(String memberFirstNAme) {
		this.memberFirstNAme = memberFirstNAme;
	}
	public String getMemberLastNAme() {
		return memberLastNAme;
	}
	public void setMemberLastNAme(String memberLastNAme) {
		this.memberLastNAme = memberLastNAme;
	}
	public int getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(int memberNumber) {
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
	public PaymentType getMemberPaymentType() {
		return memberPaymentType;
	}
	public void setMemberPaymentType(PaymentType memberPaymentType) {
		this.memberPaymentType = memberPaymentType;
	}
	
}
