package webchat.model;

public class Message {

	private int id;
	private int idConv;
	private int idSender;
	private int idReceiver;
	private long time;
	private String content;

	public Message() {
		this.id = -1;
		this.idConv = -1;
		this.idSender = -1;
		this.idReceiver = -1;
		this.time = 0;
		this.content = "";
	}

	public Message(int id, int idConv, int idSender, int idReceiver, long time, String content) {
		this.id = id;
		this.idConv = idConv;
		this.idSender = idSender;
		this.idReceiver = idReceiver;
		this.time = time;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdConv() {
		return idConv;
	}

	public void setIdConv(int idConv) {
		this.idConv = idConv;
	}

	public int getIdSender() {
		return idSender;
	}

	public void setIdSender(int idSender) {
		this.idSender = idSender;
	}

	public int getIdReceiver() {
		return idReceiver;
	}

	public void setIdReceiver(int idReceiver) {
		this.idReceiver = idReceiver;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
