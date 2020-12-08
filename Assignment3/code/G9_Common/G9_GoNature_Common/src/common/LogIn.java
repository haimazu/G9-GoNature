package common;

public class LogIn {
	
	private String  userName;
	private String password;
	private Boolean LogIn=null;
	private Boolean LogOut=null;
	private Employee employee;
	
	public LogIn(Employee employee) {
		this.userName=employee.getEmployeeUserName();
		this.password=employee.getEmployeePassword();
	}
	
	
	private boolean CheckUserName(String userName) {
		
		if(this.userName.equals(userName))
			return true;
		return false;
	}
	private boolean CheckPassword(String userName,String password) {
		if(CheckUserName(userName)) {
			if(this.password.equals(password)) {
				return true;
			}
		}
		return false;
	}
		
	//need to change with the screen - where to go what screen
	public boolean loginToGoNatuerSystem (String userName,String password) {
		
		if(CheckPassword(userName,password)) {
			System.out.println("success entring");
			LogIn=true;
			LogOut=false;
			return true;
		}
		
		return false;
	}
	
	public void  logoutToGoNatuerSystem() {
		LogIn=false;
		LogOut=true;
	}
	
	
	/*
	public setDetailsCardREader() {
		
	}
	*/
	
	
	
	
}
