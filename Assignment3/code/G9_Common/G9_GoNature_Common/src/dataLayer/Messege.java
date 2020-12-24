package dataLayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import orderData.Order;

public class Messege {
	private String message;
	private Date sentTime;
	private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private ArrayList<Object> attachments;
	private Order order;

	public Messege(String message, Order order){
		setMessage(message);
		sentTime = new Date(); 
		this.order = order;
	}
	
	public String read() {
		return ("sent time:" + formatter.format(sentTime) + "\n" +
				message + "\n");
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSentTime() {
		return sentTime;
	}

	public ArrayList<Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(ArrayList<Object> attachments) {
		this.attachments = attachments;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
