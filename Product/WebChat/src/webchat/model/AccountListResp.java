package webchat.model;

import java.util.List;

public class AccountListResp extends BaseResponse {

	private List<Account> accounts;

	public List<Account> getAccounts() {
		
		return accounts;
		
	}

	public void setAccounts(List<Account> accounts) {
		
		this.accounts = accounts;
		
	}
	
	
	
}
