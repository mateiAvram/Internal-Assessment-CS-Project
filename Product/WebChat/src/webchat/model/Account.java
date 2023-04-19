package webchat.model;

public class Account {

	// Account Credentials

	private int id;
	private String username;
	private String password;

	public Account() {
		this.id = -1;
		this.username = "";
		this.password = "";
	}

	public Account(int accountID, String username, String password) {

		this.id = accountID;
		this.username = username;
		this.password = password;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
