package webchat.business;

import java.util.Date;
import java.util.UUID;

import webchat.data.SqlDataService;
import webchat.model.Session;

public class SessionManager {

	public SessionManager() {
		
	}
	
	public String insertSession(Session session) throws Exception{
		Date now = new Date();
		String id = UUID.randomUUID().toString();
		long creationDate = now.getTime();
		long expirationDate = creationDate + (1000*60*30);
		session.setId(id);
		session.setCreationDate(creationDate);
		session.setExpirationDate(expirationDate);
		SqlDataService dataService = new SqlDataService();
		dataService.insertSession(session);
		return id;
	}
	
}
