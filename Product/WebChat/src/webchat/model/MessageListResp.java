package webchat.model;

import java.util.List;

public class MessageListResp extends BaseResponse {

	private List<Message> messages;
	private Account acc1 = new Account();
	private Account acc2 = new Account();

	public Account getAcc1() {
		return acc1;
	}

	public void setAcc1(Account acc1) {
		this.acc1 = acc1;
	}

	public Account getAcc2() {
		return acc2;
	}

	public void setAcc2(Account acc2) {
		this.acc2 = acc2;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
}
