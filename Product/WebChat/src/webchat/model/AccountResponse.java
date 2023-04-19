package webchat.model;

public class AccountResponse extends BaseResponse{
	
	private Account account;
	
	public Account getAccount() {
		return this.account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

}
