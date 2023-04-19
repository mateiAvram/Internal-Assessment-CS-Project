package webchat.model;

public class DeletePendingFriendReqReq {

	boolean isIdSender;
	int id;
	
	public boolean getIsIdSender() {
		return isIdSender;
	}
	public void setIsIdSender(boolean isIdSender) {
		this.isIdSender = isIdSender;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
