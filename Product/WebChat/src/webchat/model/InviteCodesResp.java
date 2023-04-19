package webchat.model;

import java.util.List;

public class InviteCodesResp extends BaseResponse{

	private List<String> inviteCodes;

	public List<String> getInviteCodes() {
		return inviteCodes;
	}

	public void setInviteCodes(List<String> inviteCodes) {
		this.inviteCodes = inviteCodes;
	}

}
