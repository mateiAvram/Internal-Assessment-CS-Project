package webchat.model;

public class isValidInvCodeResp {

	private boolean isValid;
	private int idAccount;
	
	public isValidInvCodeResp() {
		this.isValid = false;
		this.idAccount = -1;
	}
	
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public int getIdAccount() {
		return idAccount;
	}
	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}
	
	
	
}
