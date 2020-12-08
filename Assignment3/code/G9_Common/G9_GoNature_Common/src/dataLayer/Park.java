package dataLayer;

public class Park {
	
	private String name;
	private int maximumAmount=0;
	private int currentAmount=0;

	
	public Park(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMaximumAmount() {
		return maximumAmount;
	}
	public void setMaximumAmount(int maximumAmount) {
		this.maximumAmount = maximumAmount;
	}
	public int getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(int currentAmount) {
		this.currentAmount = currentAmount;
	}

	
	
	
}
