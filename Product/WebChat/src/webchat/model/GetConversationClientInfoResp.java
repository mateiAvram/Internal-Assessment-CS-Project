package webchat.model;

public class GetConversationClientInfoResp extends BaseResponse{

	private Account account;
	private Conversation conv;
	private MessageListResp messagesResp;
	
	public MessageListResp getMessagesResp() {
		return messagesResp;
	}
	public void setMessagesResp(MessageListResp messagesResp) {
		this.messagesResp = messagesResp;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Conversation getConv() {
		return conv;
	}
	public void setConv(Conversation conv) {
		this.conv = conv;
	}
	
}
