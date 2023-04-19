package webchat.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import webchat.data.SqlDataService;
import webchat.model.Account;
import webchat.model.Message;
import webchat.model.MessageListResp;

public class MessageManager {

	public MessageManager() {
		
	}
	
	public void insertMessage(Message message, String conversationKey) throws Exception {
		String content = message.getContent();
		Encrypt e = new Encrypt();
		content = e.encryptText(conversationKey, content);
		message.setContent(content);
		SqlDataService dataService = new SqlDataService();
		dataService.insertMessage(message);
	}
	
	public void deleteMessageByID(int idMessage) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.deleteMessage(idMessage);
	}
	
	public MessageListResp getLatestMessages(int idConversation, int id1, int id2, int limit, int offSet, String conversationKey) throws Exception {
		offSet = offSet*11;
		SqlDataService dataService = new SqlDataService();
		Decrypt d = new Decrypt();
		List<Message> list = dataService.getLatestMessages(idConversation, id1, limit, offSet);
		for(Message m : list) {
			
			String content = m.getContent();
			content = d.decryptText(conversationKey, content);
			m.setContent(content);
			
		}
		Account acc1 = dataService.getAccountByID(id1);
		acc1.setPassword("");
		Account acc2 = dataService.getAccountByID(id2);
		acc2.setPassword("");
		MessageListResp resp = new MessageListResp();
		resp.setAcc1(acc1);
		resp.setAcc2(acc2);
		resp.setMessages(list);
		return resp;
	}
	
	public MessageListResp getLatestMessagesPoll(int idConversation, int id1, int id2, int limit, int offSet, long lastUpdateClient, String conversationKey) throws Exception {
		
		
		MessageListResp resp = new MessageListResp();
		
		resp = getLatestMessages(idConversation, id1, id2, limit, offSet, conversationKey);
		
		List<Message> list = resp.getMessages();
		List<Message> listPoll = new ArrayList<Message>();
		
		Date clientD = new Date(lastUpdateClient);
		
		for(Message m : list) {
			
			long messageTime = m.getTime();
			Date d = new Date(messageTime);
			
			if(d.after(clientD)) {
				
				listPoll.add(m);
				
			}
			
		}
		
		resp.setMessages(listPoll);
		
		return resp;
		
	}
	
}
