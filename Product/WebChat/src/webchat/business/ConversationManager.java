package webchat.business;

import java.util.List;

import webchat.data.SqlDataService;
import webchat.model.Conversation;

public class ConversationManager {

	public ConversationManager() {
		
	}
	
	public Conversation getConversationByID(int idConv) throws Exception {
		Conversation c = new Conversation();
		SqlDataService dataService = new SqlDataService();
		c = dataService.getConversationByID(idConv);
		return c;
	}
	
	public int insertConversation(Conversation conv) throws Exception {
		
		SqlDataService dataService = new SqlDataService();
		dataService.insertConversation(conv);
		
		Conversation c = dataService.getConversationByIds(conv.getId1(), conv.getId2());
		
		int idConv = c.getId();
		
		return idConv;
		
	}
	
	public Conversation getConversationByIDs(int id1, int id2) throws Exception {
		SqlDataService dataService = new SqlDataService();
		Conversation conv = dataService.getConversationByIds(id1, id2);
		return conv;
	}
	
	public void updateConversation(Conversation conv) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.updateConversation(conv);
	}

	public void deleteConversationByIDs(int id1, int id2) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.deleteConversationByIds(id1, id2);
	}
	
	public List<Conversation> getAcitveConversationsByID(int accountID) throws Exception {
		SqlDataService dataService = new SqlDataService();
		List<Conversation> list = dataService.getActiveConversationsByID(accountID);
		return list;
	}
	
	public List<Conversation> getActiveConversationsForID(int accountID, int limit, int offSet) throws Exception {
		offSet = offSet * 9;
		SqlDataService dataService = new SqlDataService();
		List<Conversation> list = dataService.getActiveConversationsForID(accountID, limit, offSet);
		return list;
		
	}
	
//	public List<Conversation> getAcitveConversationsByID(int accountID, int limit, int offSet) throws Exception {
//		SqlDataService dataService = new SqlDataService();
//		List<Conversation> list = dataService.getActiveConversationsByID(accountID, limit, offSet);
//		return list;
//	}
	
}
