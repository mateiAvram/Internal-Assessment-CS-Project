package webchat.model;

public class ConversationResponse extends BaseResponse{

	private Conversation conv;

	public Conversation getConv() {
		return conv;
	}

	public void setConv(Conversation conv) {
		this.conv = conv;
	}
	
}
