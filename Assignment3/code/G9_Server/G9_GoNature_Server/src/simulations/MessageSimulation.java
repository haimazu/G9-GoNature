package simulations;

public class MessageSimulation {
	private int orderNum;
	private String message;
	private String to;
	
	MessageSimulation(int orderNum,String message,String to){
		setOrderNum(orderNum);
		setMessage(message);
		setTo(to);
	}
	
	public int getOrderNum() {
		return orderNum;
	}
	
	public void setOrderNum(int ordernum) {
		orderNum = ordernum;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String Message) {
		message = Message;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String To) {
		to = To;
	}
}