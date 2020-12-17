package orderData;

public enum OrderType {
	SINGLE("single"),
	FAMILY("family"),
	GROUP("group");
	
	public final String label;
	
	private OrderType(String label) {
        this.label = label;
    }
	
	@Override 
	public String toString() { 
	    return this.label; 
	}
}
