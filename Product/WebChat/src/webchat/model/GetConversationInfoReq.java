package webchat.model;

public class GetConversationInfoReq {

	private int idConv;
	private long lastUpdate;
	private int offSet;
	private boolean isPolling;
	
	public boolean getIsPolling() {
		return isPolling;
	}
	public void setIsPolling(boolean isPolling) {
		this.isPolling = isPolling;
	}
	public long getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public int getOffSet() {
		return offSet;
	}
	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}
	public int getIdConv() {
		return idConv;
	}
	public void setIdConv(int idConv) {
		this.idConv = idConv;
	}
	
}
