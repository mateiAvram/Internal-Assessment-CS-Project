package webchat.model;

public class NewAccountReq {

	private Account account;
	private String inviteCode;
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String getInviteCode() {
		return inviteCode;
	}
	
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	
}
