package userData;

public class FamilyMember extends Member{
	
	private int familyMembersAmount;

	/**
	 * @param memberID
	 * @param memberFirstNAme
	 * @param memberLastNAme
	 * @param memberNumber
	 * @param memberPhoneNumber
	 * @param memberEmail
	 * @param memberPaymentType
	 */
	public FamilyMember(String memberID, String memberFirstNAme, String memberLastNAme, int memberNumber,
			String memberPhoneNumber, String memberEmail, PaymentType memberPaymentType,int familyMembersAmount) {
		super(memberID, memberFirstNAme, memberLastNAme, memberNumber, memberPhoneNumber, memberEmail, memberPaymentType);
		
		
		this.familyMembersAmount=familyMembersAmount;
	}

	/**
	 * 
	 */

	
	
}
