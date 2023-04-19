

package webchat.model;

import java.util.Random;

public class InviteCode {

	private int id;
	private int id_a;
	private String code;
	
	public InviteCode() {
		
		this.id_a = -1;
		this.code = "";
	}
	
	public void generateCode() {
		String code = "";
		String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rand = new Random();
		int random = 0;
		for(int index = 0; index < 5; index++) {
			random = rand.nextInt(62);
			code = code + characters.charAt(random);
		}
		this.code = code;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId_a(int id_a) {
		this.id_a = id_a;
	}
	
	public int getId_a() {
		return this.id_a;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

}