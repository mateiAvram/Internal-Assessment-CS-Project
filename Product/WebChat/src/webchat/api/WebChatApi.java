package webchat.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webchat.model.ActiveConversationListResp;
import webchat.model.Message;
import webchat.model.NewMessageReq;
import webchat.business.MessageManager;
import webchat.model.GetConversationClientInfoResp;
import webchat.model.GetConversationInfoReq;
import webchat.model.MessageListResp;
import webchat.business.ConversationManager;
import webchat.model.Conversation;
import webchat.model.ConversationResponse;
import webchat.model.DeletePendingFriendReqReq;
import webchat.model.AccountListResp;
import webchat.model.UpdateAccountReq;
import webchat.business.AuthenticationManager;
import webchat.business.AccountManager;
import webchat.business.InviteCodeManager;
import webchat.model.Account;
import webchat.model.AccountInfoResp;
import webchat.model.AccountResponse;
import webchat.model.BaseResponse;
import webchat.model.InviteCode;
import webchat.model.InviteCodesResp;
import webchat.model.NewAccountReq;
import webchat.model.isValidInvCodeResp;

@Path("/app")
public class WebChatApi {

	@Context private HttpServletRequest httpRequest;
	@Context private HttpServletResponse httpResponse;
	
	/**
	 * API that creates a NEW account.
	 * It receives all the details of an account (!VERY IMPORTANT! Every field has to MATCH the ones from the Account class in the models package).
	 * This is because an automatically mapping process takes place right before the AJAX HTTP request gets here.
	 * The exact fields are:
	 * id, username, password
	 * @param newAccount (Account object)
	 * @return A BaseResponse object that contains error code and description
	 */
	@POST
	@Path("/newAccount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse createAccount(NewAccountReq req) {
		
		AccountResponse resp = new AccountResponse();
		
		try {
			
			Account newAccount = req.getAccount();
			String inviteCode = req.getInviteCode();
			
			AccountManager am = new AccountManager();
			InviteCodeManager icm = new InviteCodeManager();
			
			if(newAccount.getUsername() == null || newAccount.getUsername().isEmpty()) {
				throw new Exception();
			}
			if(newAccount.getPassword() == null || newAccount.getPassword().isEmpty()) {
				throw new Exception();
			}
			
			isValidInvCodeResp invCodeResp = new isValidInvCodeResp();
			invCodeResp = icm.isValidInviteCode(inviteCode);
			
			if(!invCodeResp.isValid()) {
				
				throw new Exception ("Invalid Invite Code!");
				
			}
			
			int invCodeAccountID = invCodeResp.getIdAccount();
			
			if(!inviteCode.equals("paRa")) {
				
				icm.deleteInviteCodeByCode(inviteCode);
			}
				
			newAccount = am.insertAccount(newAccount);
			int AccountID = newAccount.getId();
			InviteCode newInviteCode = new InviteCode();
			am.signUpAddFriends(AccountID, invCodeAccountID);
			newInviteCode.setId_a(AccountID);
			newInviteCode.generateCode();
			icm.insertInviteCode(newInviteCode);
			
			newAccount.setPassword("");
			resp.setAccount(newAccount);
				
			
		} catch (Exception e) {
			resp.setErrorByException(e);
		}
		
		return resp;
		
	}
	
	/**
	 * API that checks for active session.
	 * @throws Exception 
	 */
	@POST
	@Path("/checkSession")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse checkSession() {
		
		BaseResponse resp = new AccountResponse();
		
		AuthenticationManager authM = new AuthenticationManager();
		try {
			
			if(authM.isAuth(httpRequest)) {
				
				resp.setErrorMessage("Active Session!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that verifies an account password.
	 * It receives all the data from the log in form and verifies it on the server for security purposes.
	 * The exact fields verified are:
	 * id, password
	 * @throws Exception 
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse logIn(Account accountVerify) {
		
		BaseResponse resp = new AccountResponse();
		
		AuthenticationManager authM = new AuthenticationManager();
		try {
			
			resp = authM.logIn(accountVerify, httpResponse, httpRequest);
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that verifies an account password.
	 * It receives all the data from the log in form and verifies it on the server for security purposes.
	 * The exact fields verified are:
	 * id, password
	 * @throws Exception 
	 */
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse logIn() {
		
		BaseResponse resp = new BaseResponse();
		
		AuthenticationManager authM = new AuthenticationManager();
		try {
			
			authM.logOut(httpRequest, httpResponse);
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that returns a single account on the response based on the requested account ID.
	 * @param accountID
	 * @return An AccountResponse object(inheriting BaseResponse class)that contains error code and description (from the BaseResponse class) and an Account entity
	 */
	@GET
	@Path("/getAccountInfo")
	@Produces({MediaType.APPLICATION_JSON})
	public BaseResponse getAccountByID() {
		
		AccountInfoResp resp = new AccountInfoResp();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int accountID = cookieAccountID;
				
				AccountManager am = new AccountManager();
				Account account = am.getAccountByID(accountID);
				account.setPassword("");
				resp.setAccount(account);
				
				InviteCodeManager icm = new InviteCodeManager();
				
				List<String> inviteCodes = icm.getInviteCodesById(cookieAccountID);
				resp.setInviteCodes(inviteCodes);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that UPDATES an EXISTING account based on the accountID.
	 * It receives all the details of an account (!VERY IMPORTANT! Every field has to MATCH the ones from the Account class in the models package).
	 * This is because an automatically mapping process takes place right before the AJAX HTTP request gets here.
	 * The exact fields are:
	 * id, username, password
	 * @param UpdateAccountReq
	 * @return A BaseResponse object that contains error code and description
	 */
	@PUT
	@Path("/updateAccount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse update(UpdateAccountReq req) {
		
		BaseResponse resp = new BaseResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int accountID = cookieAccountID;
				
				Account accountToUpdate = req.getAccount();
				accountToUpdate.setId(accountID);
				String password = req.getOldPassword();
				AccountManager am = new AccountManager();
				Account account = am.getAccountByID(accountToUpdate.getId());
				if(!password.contentEquals(account.getPassword())) {
					throw new Exception ("Incorrect Password!");
				}
				
				am.updateAccount(accountToUpdate);
				
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that DELETES an EXISTING account based on the provided AccountID and Password to confirm on the AJAX call.
	 * @param DeleteAccountReq
	 * @return A BaseResponse object that contains error code and description
	 */

	@DELETE
	@Path("/deleteAccount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse deleteAccountByID(String encryptedPassword) {
		BaseResponse resp = new BaseResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int accountID = cookieAccountID;
				
				AccountManager am = new AccountManager();
				Account account = am.getAccountByID(accountID);
				
				//Metoda provizorie!
				
				String password = encryptedPassword.substring(1, encryptedPassword.length()-1);
				
				boolean val = password.contentEquals(account.getPassword());
					
				if(!password.contentEquals(account.getPassword())) {
					
					throw new Exception ("Incorrect Password!");
					
				}
					
				am.deleteAccountByID(accountID);
				
				authM.logOut(httpRequest, httpResponse);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that returns the list of friends of a user
	 * @return A AccountListResp object (inheriting BaseRespponse class) that contains error code and description (from the BaseResponse class)
	 * and a list of Account entities
	 */
	@GET
	@Path("/getFriendList")
	@Produces({MediaType.APPLICATION_JSON})
	public BaseResponse getFriendList() {
		
		AccountListResp resp = new AccountListResp();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				AccountManager am = new AccountManager();
				List<Account> friendList = am.getFriendListByID(cookieAccountID);
				resp.setAccounts(friendList);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that returns the list of accounts the user is trying to add to friends
	 * @return A AccountListResp object (inheriting BaseRespponse class) that contains error code and description (from the BaseResponse class)
	 * and a list of Account entities
	 */
	@GET
	@Path("/getPendingList")
	@Produces({MediaType.APPLICATION_JSON})
	public BaseResponse getPendingList() {
		
		AccountListResp resp = new AccountListResp();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int accountID = cookieAccountID;
				
				AccountManager am = new AccountManager();
				List<Account> pendingList = am.getPendingListByID(accountID);
				resp.setAccounts(pendingList);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
				
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that returns the list of accounts the user is receiving friend requests from
	 * @return A AccountListResp object (inheriting BaseRespponse class) that contains error code and description (from the BaseResponse class)
	 * and a list of Account entities
	 */
	@GET
	@Path("/getIncomingList")
	@Produces({MediaType.APPLICATION_JSON})
	public BaseResponse getIncomingList() {
		
		AccountListResp resp = new AccountListResp();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int accountID = cookieAccountID;
				
				AccountManager am = new AccountManager();
				List<Account> incomingList = am.getIncomingListByID(accountID);
				resp.setAccounts(incomingList);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
				
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that returns the list of accounts the user blocked
	 * @return A AccountListResp object (inheriting BaseRespponse class) that contains error code and description (from the BaseResponse class)
	 * and a list of Account entities
	 */
	@GET
	@Path("/getBlockedList")
	@Produces({MediaType.APPLICATION_JSON})
	public BaseResponse getBlockedList() {
		
		AccountListResp resp = new AccountListResp();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int accountID = cookieAccountID;
				
				AccountManager am = new AccountManager();
				List<Account> blockedList = am.getBlockedListByID(accountID);
				resp.setAccounts(blockedList);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that accepts another users friend request.
	 * It receives the account IDs and sets them as friends.
	 */
	@POST
	@Path("/sendFriendReq")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse sendFriendReq(int idUserReceiver) {
		
		AccountResponse resp = new AccountResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int idUserSender = cookieAccountID;
				
				AccountManager am = new AccountManager();
				if(idUserReceiver == -1) {
					throw new Exception ("Invalid ID");
				}
				if(idUserSender == idUserReceiver) {
					throw new Exception("Identical ID's");
				}
				List<Account>friendsIdUserSender = am.getFriendListByID(idUserSender);
				List<Account>blockedidUserSender = am.getBlockedListByID(idUserSender);
				for(Account account : friendsIdUserSender) {
					if(account.getId() == idUserReceiver) {
						throw new Exception("Users are already Friends");
					}
				}
				for(Account account : blockedidUserSender) {
					if(account.getId() == idUserReceiver) {
						throw new Exception("This user has been blocked");
					}
				}
				am.sendFriendReq(idUserSender, idUserReceiver);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that accepts another users friend request.
	 * It receives the account IDs and sets them as friends.
	 */
	@POST
	@Path("/acceptFriendReq")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse acceptFriendReq(int idUserSender) {
		
		AccountResponse resp = new AccountResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int idUserReceiver = cookieAccountID;
				AccountManager am = new AccountManager();
				am.acceptFriendReq(idUserSender, idUserReceiver);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that DELETES an EXISTING pendingReq based on the provided AccountID.
	 * @param DeletePendingReq
	 * @return A BaseResponse object that contains error code and description
	 */

	@DELETE
	@Path("/deleteFriendReq")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse deletePendingReqByIDs(DeletePendingFriendReqReq req) {
		
		BaseResponse resp = new BaseResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				boolean isIdSender = req.getIsIdSender();
				int idUserSender = -1;
				int idUserReceiver = -1;
				
				if(isIdSender) {
					
					idUserSender = req.getId();
					idUserReceiver = cookieAccountID;
					
				} else {
					
					idUserSender = cookieAccountID;
					idUserReceiver = req.getId();
					
				}
				
				AccountManager am = new AccountManager();
				am.deleteFriendReq(idUserSender, idUserReceiver);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that removes a friend of the user based on the accountIDs.
	 * @param RemoveFriend
	 * @return A BaseResponse object that contains error code and description
	 */

	@DELETE
	@Path("/removeFriend")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse removeFriend(int idUserFriend) {
		
		BaseResponse resp = new BaseResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int idUser = cookieAccountID;
				
				AccountManager am = new AccountManager();
				am.removeFriend(idUser, idUserFriend);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that blocks a user based on the provided AccountIDs.
	 * It receives the account IDs.
	 */
	@POST
	@Path("/blockUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse blockUser(int idBlockedUser) {
		
		AccountResponse resp = new AccountResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int idUser = cookieAccountID;
				
				AccountManager am = new AccountManager();
				am.blockUser(idUser, idBlockedUser);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that unblocks a user based on the provided AccountIDs.
	 * @param DeleteBlockEntry
	 * @return A BaseResponse object that contains error code and description
	 */

	@DELETE
	@Path("/unblockUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse unblockUser(int idBlockedUser) {
		BaseResponse resp = new BaseResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int idUser = cookieAccountID;
				AccountManager am = new AccountManager();
				am.unblockUser(idUser, idBlockedUser);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
		
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that creates a NEW conversation.
	 * It receives all the details of a conversation (!VERY IMPORTANT! Every field has to MATCH the ones from the Conversation class in the models package).
	 * This is because an automatically mapping process takes place right before the AJAX HTTP request gets here.
	 * The exact fields are:
	 * id, id1, id2, lastUpdate;
	 * @param newConversation (Conversation object)
	 * @return A BaseResponse object that contains error code and description
	 */

	@POST
	@Path("/newConversation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse createConversation(int idMessageUser) {
		
		ConversationResponse resp = new ConversationResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int id1 = cookieAccountID;
				int id2 = idMessageUser;
				Date time = new Date();
				long lastUpdate = time.getTime();
				
				ConversationManager cm = new ConversationManager();
				Conversation newConversation = new Conversation();
				
				newConversation.setId1(id1);
				newConversation.setId2(id2);
				newConversation.setLastUpdate(lastUpdate);
				newConversation.generateConversationKey();
				
				List<Conversation> activeConversations = cm.getAcitveConversationsByID(id1);
				
				for(Conversation c : activeConversations) {
					
					if((c.getId1() == id1 && c.getId2() == id2) || (c.getId1() == id2 && c.getId2() == id1)) {
						
						Conversation existingConv = cm.getConversationByIDs(id1, id2);
						
						existingConv.setId1(-1);
						existingConv.setId2(-1);
						existingConv.setConversationKey("");
						
						resp.setConv(existingConv);
						throw new Exception ("Conversation already exists!");
						
					}
					
				}
				
				int idConv = cm.insertConversation(newConversation);
				newConversation.setId(idConv);
				
				resp.setConv(newConversation);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
	
	}
	
	/**
	 * API that returns the information needed for a conversation page
	 * It receives two account iDs
	 * @return A GetConversationClientInfoResponse object (inheriting BaseRespponse class) that contains error code and description (from the BaseResponse class)
	 * and useful information for client side
	 */
	@POST
	@Path("/getConversationInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse getConversationInfo(GetConversationInfoReq req) {
		
		GetConversationClientInfoResp resp = new GetConversationClientInfoResp();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				int convId = req.getIdConv();
				
				boolean flag = false;
				String cookieName = "conversationKeyOfId_" + convId;
				String conversationKey = "";
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
					if(cookie.getName().contentEquals(cookieName)) {
						
						flag = true;
						conversationKey = cookie.getValue();
						
					}
					
				}
				
				ConversationManager cm = new ConversationManager();
				Conversation c = cm.getConversationByID(convId);
				
				int id1 = c.getId1();
				int id2 = c.getId2();
				
				if(cookieAccountID != id1 && cookieAccountID != id2) {
					
					throw new Exception ("Access Denied!");
					
				}
				
				if(!flag) {
					
					conversationKey = c.getConversationKey();
					Cookie cConvKey = new Cookie(cookieName, conversationKey);
					cConvKey.setMaxAge(-1);
					httpResponse.addCookie(cConvKey);
					
				}
				
				long lastUpdateClient = req.getLastUpdate();
				int offSet = req.getOffSet();
				int limit = 11;
				boolean isPolling = req.getIsPolling();
				
				AccountManager am = new AccountManager();
				MessageManager mm = new MessageManager();
				
				Account acc = new Account();
				
				if(cookieAccountID != id1) {
					
					acc = am.getAccountByID(id1);
					
				} else {
					
					acc = am.getAccountByID(id2);
					
				}
				
				c.setConversationKey("");
				c.setId1(-1);
				c.setId2(-1);
				
				acc.setPassword("");
				
				MessageListResp messagesResp = new MessageListResp();
				
				if(!isPolling) {
					
					messagesResp = mm.getLatestMessages(convId, id1, id2, limit, offSet, conversationKey);
					
				} else {
					
					messagesResp = mm.getLatestMessagesPoll(convId, id1, id2, limit, offSet, lastUpdateClient, conversationKey);
					
				}
				
				resp.setMessagesResp(messagesResp);
				resp.setAccount(acc);
				resp.setConv(c);
				
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that returns active conversations
	 * @return A ConversationListResp object (inheriting BaseRespponse class) that contains error code and description (from the BaseResponse class)
	 * and useful information for client side
	 */
	@POST
	@Path("/getActiveConversations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse getActiveConversations(int offSet) {
		
		ActiveConversationListResp resp = new ActiveConversationListResp();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				int limit = 9;
				
				ConversationManager cm = new ConversationManager();
				List<Conversation> cList = cm.getActiveConversationsForID(cookieAccountID, limit, offSet);
				
				AccountManager am = new AccountManager();
				List<Account> aList = new ArrayList<Account>();
				
				for(Conversation c : cList) {
					
					int id1 = c.getId1();
					int id2 = c.getId2();
					
					Account a = new Account();
					
					if(cookieAccountID == id1) {
						
						a = am.getAccountByID(id2);
						
					} else {
						
						a = am.getAccountByID(id1);
						
					}
					
					a.setPassword("");
					
					aList.add(a);
					
				}
				
				resp.setConversations(cList);
				resp.setAccounts(aList);

				return resp;
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that creates a NEW message.
	 * It receives all the details of a message (!VERY IMPORTANT! Every field has to MATCH the ones from the Message class in the models package).
	 * This is because an automatically mapping process takes place right before the AJAX HTTP request gets here.
	 * The exact fields are:
	 * id, idConv, idSender, idReciver, time, content;
	 * @param newMessage (Message object)
	 * @return A BaseResponse object that contains error code and description
	 */

	@POST
	@Path("/newMessage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse createMessage(NewMessageReq req) {
		
		ConversationResponse resp = new ConversationResponse();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				int idConv = req.getIdConv();
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				String cookieName = "conversationKeyOfId_" + idConv;
				String conversationKey = "";
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
					if(cookie.getName().contentEquals(cookieName)) {
						
						conversationKey = cookie.getValue();
						
					}
					
				}
				
				int idSender = cookieAccountID;
				
				ConversationManager cm = new ConversationManager();
				Conversation c = cm.getConversationByID(idConv);
				int id1 = c.getId1();
				int id2 = c.getId2();
				
				int idReciver = -1;
				
				if(id1 == idSender) {
					
					idReciver = id2;
					
				} else {
					
					idReciver = id1;
					
				}
				
				Date time = new Date();
				long lastUpdate = time.getTime();
				
				MessageManager mm = new MessageManager();
				Message newMessage = new Message();
				
				String content = req.getContent();
				
				newMessage.setIdConv(idConv);
				newMessage.setIdSender(idSender);
				newMessage.setIdReceiver(idReciver);
				newMessage.setTime(lastUpdate);
				newMessage.setContent(content);
				
				mm.insertMessage(newMessage, conversationKey);
				
				c.setLastUpdate(lastUpdate);
				
				cm.updateConversation(c);
				
				c.setConversationKey("");
				c.setId1(-1);
				c.setId2(-1);
				
				resp.setConv(c);
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that searches through a list of the users friends.
	 * It receives a string and selects the users that contain that string in their username.
	 */
	@POST
	@Path("/searchFriends")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse sendFriendReq(String str) {
		
		AccountListResp resp = new AccountListResp();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				str = str.substring(1, str.length()-1);
				AccountManager am = new AccountManager();
				resp.setAccounts(am.getSearchAccountResults(str, cookieAccountID));
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
	/**
	 * API that gets the invite codes of a user.
	 * It receives an Integer (id) and returns a list of strings (inviteCodes)
	 */
	@POST
	@Path("/inviteCodes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse getInviteCodes() {
		
		InviteCodesResp resp = new InviteCodesResp();
		
		try {
			
			AuthenticationManager authM = new AuthenticationManager();
			
			if(authM.isAuth(httpRequest)) {
				
				Cookie [] cookieList = httpRequest.getCookies();
				
				int cookieAccountID = -1;
				
				for(Cookie cookie : cookieList) {
					
					if(cookie.getName().contentEquals("accountID")) {
						
						cookieAccountID = Integer.valueOf(cookie.getValue());
						
					}
					
				}
				
				
				
			} else {
				
				throw new Exception ("Session has Expired!");
				
			}
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
			
		}
		
		return resp;
		
	}
	
}
