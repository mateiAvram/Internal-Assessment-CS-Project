

package webchat.model;

import java.util.Random;

public class Conversation {

	private int id;
	private int id1;
	private int id2;
	private long lastUpdate;
	private String conversationKey;

	public Conversation() {
		this.id = 0;
		this.id1 = 0;
		this.id2 = 0;
		this.lastUpdate = 0;
		this.conversationKey = "";
	}

	public Conversation(int id, int id1, int id2, long lastUpdate, String conversationKey) {
		this.id = id;
		this.id1 = id1;
		this.id2 = id2;
		this.lastUpdate = lastUpdate;
		this.conversationKey = conversationKey;
	}
	
	public void generateConversationKey() {
		
		String code = "";
		String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rand = new Random();
		int random = 0;
		for(int i = 0 ; i < 7; i++) {
			random = rand.nextInt(62);
			code = code + characters.charAt(random);
		}
		
		this.conversationKey = code;
		
	}

	public String getConversationKey() {
		return conversationKey;
	}
	
	public void setConversationKey(String conversationKey) {
		this.conversationKey = conversationKey;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId1() {
		return id1;
	}

	public void setId1(int id1) {
		this.id1 = id1;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
