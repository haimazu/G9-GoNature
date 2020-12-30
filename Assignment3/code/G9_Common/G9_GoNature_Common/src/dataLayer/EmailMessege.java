package dataLayer;

import orderData.Order;

public class EmailMessege extends Messege{
	private String to;
	private EmailMessege repliedTo;
	
	public EmailMessege(String to, String messege, Order order) {
		super(messege,order);
		this.to = to;
	}
	
	public String read() {
		return "To: " + to + "\n" + super.read();
	}
	
	public EmailMessege replay(String to, String messege) {
		EmailMessege replayMsg = new EmailMessege(to,messege,this.getOrder());
		replayMsg.setRepliedTo(this);
		return replayMsg;
	}
	
	public EmailMessege getRepliedTo() {
		return repliedTo;
	}

	public void setRepliedTo(EmailMessege repliedTo) {
		this.repliedTo = repliedTo;
	}
}
