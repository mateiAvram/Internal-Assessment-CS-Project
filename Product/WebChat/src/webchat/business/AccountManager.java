package webchat.business;

import java.util.ArrayList;
import java.util.List;

import webchat.data.SqlDataService;
import webchat.model.Account;

public class AccountManager {

	public AccountManager() {
		
	}
	
	public Account insertAccount(Account newAccount) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.insertAccount(newAccount);
		newAccount = dataService.getLastAccount();
		return newAccount;
	}
	
	public Account getAccountByID(int accountID) throws Exception {
		SqlDataService dataService = new SqlDataService();
		Account account = dataService.getAccountByID(accountID);
		return account;
	}
	
	public void updateAccount(Account account) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.updateAccount(account);
	}
	
	public void deleteAccountByID(int accountID) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.deleteAccount(accountID);
	}
	
	public void signUpAddFriends(int idUser1, int idUser2) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.insertFriend(idUser1, idUser2);
		dataService.insertFriend(idUser2, idUser1);
	}
	
	public void sendFriendReq(int idUserSender, int idUserReceiver) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.insertFriendReq(idUserSender, idUserReceiver);
	}
	
	public void acceptFriendReq(int idUserSender, int idUserReceiver) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.deleteFriendReqByID(idUserSender, idUserReceiver);
		dataService.insertFriend(idUserSender, idUserReceiver);
		dataService.insertFriend(idUserReceiver, idUserSender);
	}
	
	public void deleteFriendReq(int idUserSender, int idUserReceiver) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.deleteFriendReqByID(idUserSender, idUserReceiver);
	}
	
	public void removeFriend(int idUser, int idUserFriend) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.deleteFriend(idUser, idUserFriend);
		dataService.deleteFriend(idUserFriend, idUser);
	}
	
	public void blockUser(int idUser, int idBlockedUser) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.deleteFriend(idUser, idBlockedUser);
		dataService.insertBlocked(idUser, idBlockedUser);
	}
	
	public void unblockUser(int idUser, int idBlockedUser) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.deleteBlockedByID(idUser, idBlockedUser);
		dataService.insertFriend(idUser, idBlockedUser);
	}
	
	public List<Account> getFriendListByID(int accountID) throws Exception {
		SqlDataService dataService = new SqlDataService();
		List<Account> list = dataService.getFriendListByID(accountID);
		return list;
	}
	
	public List<Account> getPendingListByID(int accountID) throws Exception {
		SqlDataService dataService = new SqlDataService();
		List<Account> list = dataService.getPendingListByID(accountID);
		return list;
	}
	
	public List<Account> getIncomingListByID(int accountID) throws Exception {
		SqlDataService dataService = new SqlDataService();
		List<Account> list = dataService.getIncomingListByID(accountID);
		return list;
	}
	
	public List<Account> getBlockedListByID(int accountID) throws Exception {
		SqlDataService dataService = new SqlDataService();
		List<Account> list = dataService.getBlockedListByID(accountID);
		return list;
	}
	
	public List<Account> getSearchAccountResults(String str, int accountID) throws Exception {
		SqlDataService dataService = new SqlDataService();
		List<Account> friendList = dataService.getFriendListByID(accountID);
		List<Account> searchResults = new ArrayList<Account>();
		for(Account a : friendList) {
			String username = a.getUsername();
			if(username.contains(str)) {
				searchResults.add(a);
			}
		}
		return searchResults;
	}
	
}
