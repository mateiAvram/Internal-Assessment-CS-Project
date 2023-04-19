package webchat.model;

public class NewMessageReq {

	private int idConv;
	private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIdConv() {
		return idConv;
	}
	public void setIdConv(int idConv) {
		this.idConv = idConv;
	}
	
}
