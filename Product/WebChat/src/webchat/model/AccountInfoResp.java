package webchat.model;

import java.util.List;

public class AccountInfoResp extends BaseResponse{

	private Account account;
	private List<String> inviteCodes;
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public List<String> getInviteCodes() {
		return inviteCodes;
	}
	public void setInviteCodes(List<String> inviteCodes) {
		this.inviteCodes = inviteCodes;
	}
	
	
}
