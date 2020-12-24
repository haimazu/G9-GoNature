package dataLayer;

import orderData.Order;

public class SmsMessege extends Messege{
	private String to;
	private SmsMessege repliedTo;
	
	public SmsMessege(String to, String messege, Order order) {
		super(messege,order);
		this.to = to;
	}
	
	public String read() {
		return "To: " + to + "\n" + super.read();
	}
	
	public SmsMessege replay(String to, String messege, Order order) {
		SmsMessege replayMsg = new SmsMessege(to,messege,order);
		replayMsg.setRepliedTo(this);
		return replayMsg;
	}
	
	public SmsMessege getRepliedTo() {
		return repliedTo;
	}

	public void setRepliedTo(SmsMessege repliedTo) {
		this.repliedTo = repliedTo;
	}
}
