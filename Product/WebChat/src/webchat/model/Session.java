package webchat.model;

import java.util.UUID;

public class Session {

	private String id;
	private long creationDate;
	private long expirationDate;
	
	public Session() {
		
	}
	
	public Session(long creationDate, long expirationDate, boolean active) {
		this.id = UUID.randomUUID().toString();
		this.creationDate = creationDate;
		this.expirationDate = expirationDate;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
	public long getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(long expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
