package webchat.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import webchat.model.Account;
import webchat.model.Conversation;
import webchat.model.InviteCode;
import webchat.model.Message;
import webchat.model.Session;

public class SqlDataService {

	// TODO review carefully the db file settings:

//	1. uncomment this for static hard-coded db file name
//	public static final String SQLITE_DATABASE_ABSOLUTE_PATH = "/home/para/Desktop/workspace/webchat/webchat.db"; // Linux
	public static final String SQLITE_DATABASE_ABSOLUTE_PATH = "C:/Users/paRa/Desktop/workspace/WebChat/webchat.db"; // Windows
//	public static final String SQLITE_DATABASE_ABSOLUTE_PATH = "C:/sqlite/webchat.db"; // Windows
	public static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:" + SQLITE_DATABASE_ABSOLUTE_PATH;

//	2. uncomment this for dynamic db file name set in tomcat server.xml file
//	public static final String SQLITE_DATABASE_ABSOLUTE_PATH_PROPERTY_NAME = "dbFile";
//	public static String SQLITE_DATABASE_ABSOLUTE_PATH = "";
//	public static String SQLITE_CONNECTION_STRING = "";

//	tables constants (make sure they are the same with the one in the db stucture

//	table columns (make sure they are the same with the one in the db structure


//	account table
	public static final String TABLE_ACCOUNTS = "accounts";

//	account columns

	public static final String FIELD_ACCOUNT_ID = "id";
	public static final String FIELD_ACCOUNT_USERNAME = "username";
	public static final String FIELD_ACCOUNT_PASSWORD = "password";
	public static final String FIELD_ACCOUNT_INVITE_CODE = "inviteCode";

//	inviteCode table
	public static final String TABLE_INVITE_CODE = "inviteCodes";

//	inviteCode columns

	public static final String FIELD_INVITE_CODE_ID = "id";
	public static final String FIELD_INVITE_CODE_ID_USER = "id_a";
	public static final String FIELD_INVITE_CODE_INVITE_CODE = "inviteCode";

//	friends table
	public static final String TABLE_FRIENDS = "friends";

//	friends columns
	public static final String FIELD_FRIEND_ID = "id";
	public static final String FIELD_FRIEND_ID_USER = "idUser";
	public static final String FIELD_FRIEND_ID_USER_FRIEND = "idUserFriend";

//	friendReq table
	public static final String TABLE_FRIEND_REQ = "friendReq";

//	friendReq columns
	public static final String FIELD_FRIEND_REQ_ID = "id";
	public static final String FIELD_FRIEND_REQ_ID_USER_SEND = "idUserSend";
	public static final String FIELD_FRIEND_REQ_ID_USER_RECEIVE = "idUserReceive";

//	blocked table
	public static final String TABLE_BLOCKED = "blocked";

// blocked columns
	public static final String FIELD_BLOCKED_ID = "id";
	public static final String FIELD_BLOCKED_ID_USER = "idUser";
	public static final String FIELD_BLOCKED_ID_BLOCKED_USER = "idBlockedUser";

//	conversations table
	public static final String TABLE_CONVERSATIONS = "conversations";

//	conversations columns
	public static final String FIELD_CONVERSATIONS_ID = "id";
	public static final String FIELD_CONVERSATIONS_ID1 = "id1";
	public static final String FIELD_CONVERSATIONS_ID2 = "id2";
	public static final String FIELD_CONVERSATIONS_LAST_UPDATE = "lastUpdate";
	public static final String FIELD_CONVERSATIONS_CONVERSATIONKEY = "conversationKey";

//	messages table
	public static final String TABLE_MESSAGES = "messages";

//	messages columns
	public static final String FIELD_MESSAGES_ID = "id";
	public static final String FIELD_MESSAGES_ID_CONV = "idConv";
	public static final String FIELD_MESSAGES_ID_SENDER = "idSender";
	public static final String FIELD_MESSAGES_ID_RECEIVER = "idReceiver";
	public static final String FIELD_MESSAGES_DATETIME = "dateTime";
	public static final String FIELD_MESSAGES_CONTENT = "content";

//	sessions table
	public static final String TABLE_SESSIONS = "sessions";

//	sessions columns
	public static final String FIELD_SESSIONS_ID = "id";
	public static final String FIELD_SESSIONS_CREATION_DATE = "creationDate";
	public static final String FIELD_SESSIONS_EXPIRATION_DATE = "expirationDate";

	private static Connection connection;

	private void connect() {
		try {

			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(SQLITE_CONNECTION_STRING);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public static void closeQuietly(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				/* ignored */}
		}
	}

	public void executeQuery(String query) throws Exception {
		Statement st;
		try {
			connect();
			st = connection.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}
	}

	// Accounts

	public Account getLastAccount() throws Exception {

		String query = "SELECT * FROM " + TABLE_ACCOUNTS + " ORDER BY " + FIELD_ACCOUNT_ID + " DESC LIMIT 1;";
		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		Account a = new Account();

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				int id = resultSet.getInt(FIELD_ACCOUNT_ID);
				String username = resultSet.getString(FIELD_ACCOUNT_USERNAME);
				String password = resultSet.getString(FIELD_ACCOUNT_PASSWORD);
				a.setId(id);
				a.setUsername(username);
				a.setPassword(password);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}

		return a;

	}

	public Account getAccountByID(int accountID) throws Exception {

		String query = "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + FIELD_ACCOUNT_ID + " = " + accountID + ";";
		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		Account a = new Account();

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				int id = resultSet.getInt(FIELD_ACCOUNT_ID);
				String username = resultSet.getString(FIELD_ACCOUNT_USERNAME);
				String password = resultSet.getString(FIELD_ACCOUNT_PASSWORD);
				a.setId(id);
				a.setUsername(username);
				a.setPassword(password);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}

		return a;

	}

	public void insertAccount(Account account) throws Exception {

		String query = String.format("INSERT INTO %s(%s, %s) VALUES( '%s', '%s');", TABLE_ACCOUNTS,
				FIELD_ACCOUNT_USERNAME, FIELD_ACCOUNT_PASSWORD, account.getUsername(), account.getPassword());

		System.out.println(query);
		this.executeQuery(query);

	}

	public void updateAccount(Account account) throws Exception {

		String query = String.format("UPDATE %s SET %s = '%s', %s = '%s' WHERE %s = '%s';", TABLE_ACCOUNTS,
				FIELD_ACCOUNT_USERNAME, account.getUsername(), FIELD_ACCOUNT_PASSWORD, account.getPassword(),
				FIELD_ACCOUNT_ID, account.getId());

		System.out.println(query);
		this.executeQuery(query);

	}

	public void deleteAccount(int accountID) throws Exception {

		String query = String.format("DELETE FROM %s WHERE %s = '%s';", TABLE_ACCOUNTS, FIELD_ACCOUNT_ID, accountID);

		System.out.println(query);
		this.executeQuery(query);

	}

	// InviteCodes

	public void insertInviteCode(InviteCode inviteCode) throws Exception {

		String query = String.format("INSERT INTO %s(%s, %s) VALUES( '%s', '%s');", TABLE_INVITE_CODE,
				FIELD_INVITE_CODE_ID_USER, FIELD_INVITE_CODE_INVITE_CODE, inviteCode.getId_a(), inviteCode.getCode());

		System.out.println(query);
		this.executeQuery(query);

	}

	public void deleteInviteCode(String inviteCode) throws Exception {

		String query = String.format("DELETE FROM %s WHERE %s = '%s';", TABLE_INVITE_CODE,
				FIELD_INVITE_CODE_INVITE_CODE, inviteCode);

		System.out.println(query);
		this.executeQuery(query);

	}

	public int getIdAccountInviteCode(String inviteCode) throws Exception {

		String query = String.format("SELECT " + FIELD_INVITE_CODE_ID_USER + " FROM " + TABLE_INVITE_CODE + " WHERE "
				+ FIELD_INVITE_CODE_INVITE_CODE + " = " + "'" + inviteCode + "'");
		System.out.println(query);

		Statement statement;
		ResultSet resultSet;
		int resp = -1;

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			resp = resultSet.getInt(FIELD_INVITE_CODE_ID_USER);

			return resp;

		} catch (Exception e) {

			throw e;

		} finally {

			closeQuietly(connection);

		}

	}
	
	public List<String> getInviteCodesById(int idAccount) throws Exception {
		
		List<String> list = new ArrayList<String>();
		
		String query = String.format("SELECT * FROM %s WHERE %s = '%s';",TABLE_INVITE_CODE, FIELD_INVITE_CODE_ID_USER, idAccount);

		System.out.println(query);

		Statement statement;
		ResultSet resultSet;
		
		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String inviteCode = resultSet.getString(FIELD_INVITE_CODE_INVITE_CODE);
				list.add(inviteCode);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}
		
		return list;
		
	}

	// Friends

	public void insertFriend(int idUser, int idUserFriend) throws Exception {

		String query = String.format("INSERT INTO %s(%s, %s) VALUES( '%s', '%s');", TABLE_FRIENDS, FIELD_FRIEND_ID_USER,
				FIELD_FRIEND_ID_USER_FRIEND, idUser, idUserFriend);

		System.out.println(query);
		this.executeQuery(query);

	}

	public void deleteFriend(int idUser, int idUserFriend) throws Exception {

		String query = String.format("DELETE FROM %s WHERE %s = '%s' AND %s = '%s'", TABLE_FRIENDS,
				FIELD_FRIEND_ID_USER, idUser, FIELD_FRIEND_ID_USER_FRIEND, idUserFriend);

		System.out.println(query);
		this.executeQuery(query);

	}

	public List<Account> getFriendListByID(int accountID) throws Exception {

		List<Account> friendList = new ArrayList<Account>();

		String query = String.format(
				"SELECT acc2.%s, acc2.%s FROM %s acc1 INNER JOIN %s ON acc1.%s = %s.%s INNER JOIN %s acc2 ON %s.%s = acc2.%s WHERE acc1.%s = '%s' ORDER BY acc2.%s ASC",
				FIELD_ACCOUNT_ID, FIELD_ACCOUNT_USERNAME, TABLE_ACCOUNTS, TABLE_FRIENDS, FIELD_ACCOUNT_ID,
				TABLE_FRIENDS, FIELD_FRIEND_ID_USER, TABLE_ACCOUNTS, TABLE_FRIENDS, FIELD_FRIEND_ID_USER_FRIEND,
				FIELD_ACCOUNT_ID, FIELD_ACCOUNT_ID, accountID, FIELD_ACCOUNT_ID);

		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			Account a = new Account();

			while (resultSet.next()) {
				int id = resultSet.getInt(FIELD_ACCOUNT_ID);
				String username = resultSet.getString(FIELD_ACCOUNT_USERNAME);
				String password = "";
				a = new Account(id, username, password);
				friendList.add(a);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}

		return friendList;

	}

	// FriendRequest Operations

	public List<Account> getPendingListByID(int accountID) throws Exception {

		List<Account> pendingList = new ArrayList<Account>();

		String query = "SELECT acc2." + FIELD_ACCOUNT_ID + ", acc2." + FIELD_ACCOUNT_USERNAME + " FROM "
				+ TABLE_ACCOUNTS + " acc1 INNER JOIN " + TABLE_FRIEND_REQ + " ON acc1." + FIELD_ACCOUNT_ID + " = "
				+ TABLE_FRIEND_REQ + "." + FIELD_FRIEND_REQ_ID_USER_SEND + " INNER JOIN " + TABLE_ACCOUNTS + " acc2 ON "
				+ TABLE_FRIEND_REQ + "." + FIELD_FRIEND_REQ_ID_USER_RECEIVE + " = acc2." + FIELD_ACCOUNT_ID
				+ " WHERE acc1." + FIELD_ACCOUNT_ID + " = " + accountID + " ORDER BY acc2." + FIELD_ACCOUNT_ID + " ASC";

		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			Account a = new Account();

			while (resultSet.next()) {
				int id = resultSet.getInt(FIELD_ACCOUNT_ID);
				String username = resultSet.getString(FIELD_ACCOUNT_USERNAME);
				String password = "";
				a = new Account(id, username, password);
				pendingList.add(a);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}

		return pendingList;

	}

	public List<Account> getIncomingListByID(int accountID) throws Exception {

		List<Account> incomingList = new ArrayList<Account>();

		String query = "SELECT acc2." + FIELD_ACCOUNT_ID + ", acc2." + FIELD_ACCOUNT_USERNAME + " FROM "
				+ TABLE_ACCOUNTS + " acc1 INNER JOIN " + TABLE_FRIEND_REQ + " ON acc1." + FIELD_ACCOUNT_ID + " = "
				+ TABLE_FRIEND_REQ + "." + FIELD_FRIEND_REQ_ID_USER_RECEIVE + " INNER JOIN " + TABLE_ACCOUNTS
				+ " acc2 ON " + TABLE_FRIEND_REQ + "." + FIELD_FRIEND_REQ_ID_USER_SEND + " = acc2." + FIELD_ACCOUNT_ID
				+ " WHERE acc1." + FIELD_ACCOUNT_ID + " = " + accountID + " ORDER BY acc2." + FIELD_ACCOUNT_ID + " ASC";

		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			Account a = new Account();

			while (resultSet.next()) {
				int id = resultSet.getInt(FIELD_ACCOUNT_ID);
				String username = resultSet.getString(FIELD_ACCOUNT_USERNAME);
				String password = "";
				a = new Account(id, username, password);
				incomingList.add(a);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}

		return incomingList;

	}

	public void deleteFriendReqByID(int idUserSender, int idUserReceiver) throws Exception {
		String query = String.format("DELETE FROM %s WHERE %s = '%s' AND %s = '%s'", TABLE_FRIEND_REQ,
				FIELD_FRIEND_REQ_ID_USER_SEND, idUserSender, FIELD_FRIEND_REQ_ID_USER_RECEIVE, idUserReceiver);

		System.out.println(query);
		this.executeQuery(query);
	}

	public void insertFriendReq(int idUserSender, int idUserReceiver) throws Exception {
		String query = String.format("INSERT INTO %s(%s, %s) VALUES( '%s', '%s');", TABLE_FRIEND_REQ,
				FIELD_FRIEND_REQ_ID_USER_SEND, FIELD_FRIEND_REQ_ID_USER_RECEIVE, idUserSender, idUserReceiver);

		System.out.println(query);
		this.executeQuery(query);
	}

	// Blocked Operations

	public void deleteBlockedByID(int idUser, int idBlockedUser) throws Exception {
		String query = String.format("DELETE FROM %s WHERE %s = '%s' AND %s = '%s'", TABLE_BLOCKED,
				FIELD_BLOCKED_ID_USER, idUser, FIELD_BLOCKED_ID_BLOCKED_USER, idBlockedUser);

		System.out.println(query);
		this.executeQuery(query);
	}

	public void insertBlocked(int idUser, int idBlockedUser) throws Exception {
		String query = String.format("INSERT INTO %s(%s, %s) VALUES( '%s', '%s');", TABLE_BLOCKED,
				FIELD_BLOCKED_ID_USER, FIELD_BLOCKED_ID_BLOCKED_USER, idUser, idBlockedUser);

		System.out.println(query);
		this.executeQuery(query);
	}

	public List<Account> getBlockedListByID(int accountID) throws Exception {

		List<Account> blockedList = new ArrayList<Account>();

		String query = "SELECT acc2." + FIELD_ACCOUNT_ID + ", acc2." + FIELD_ACCOUNT_USERNAME + " FROM "
				+ TABLE_ACCOUNTS + " acc1 INNER JOIN " + TABLE_BLOCKED + " ON acc1." + FIELD_ACCOUNT_ID + " = "
				+ TABLE_BLOCKED + "." + FIELD_BLOCKED_ID_USER + " INNER JOIN " + TABLE_ACCOUNTS + " acc2 ON "
				+ TABLE_BLOCKED + "." + FIELD_BLOCKED_ID_BLOCKED_USER + " = acc2." + FIELD_ACCOUNT_ID + " WHERE acc1."
				+ FIELD_ACCOUNT_ID + " = " + accountID + " ORDER BY acc2." + FIELD_ACCOUNT_ID + " ASC";

		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			Account a = new Account();

			while (resultSet.next()) {
				int id = resultSet.getInt(FIELD_ACCOUNT_ID);
				String username = resultSet.getString(FIELD_ACCOUNT_USERNAME);
				String password = "";
				a = new Account(id, username, password);
				blockedList.add(a);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}

		return blockedList;

	}

	// Conversations

	public Conversation getConversationByID(int idConv) throws Exception {

		String query = "SELECT * FROM " + TABLE_CONVERSATIONS + " WHERE " + FIELD_CONVERSATIONS_ID + " = " + idConv
				+ ";";
		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		Conversation c = new Conversation();

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				int id = idConv;
				int id1 = resultSet.getInt(FIELD_CONVERSATIONS_ID1);
				int id2 = resultSet.getInt(FIELD_CONVERSATIONS_ID2);
				long lastUpdate = resultSet.getLong(FIELD_CONVERSATIONS_LAST_UPDATE);
				String conversationKey = resultSet.getString(FIELD_CONVERSATIONS_CONVERSATIONKEY);
				c.setId(idConv);
				c.setId1(id1);
				c.setId2(id2);
				c.setLastUpdate(lastUpdate);
				c.setConversationKey(conversationKey);
				
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}

		return c;

	}

	public void insertConversation(Conversation conv) throws Exception {

		String query = String.format("INSERT INTO %s(%s, %s, %s, %s) VALUES( '%s', '%s', '%s', '%s');",
				TABLE_CONVERSATIONS, FIELD_CONVERSATIONS_ID1, FIELD_CONVERSATIONS_ID2, FIELD_CONVERSATIONS_LAST_UPDATE,
				FIELD_CONVERSATIONS_CONVERSATIONKEY, conv.getId1(), conv.getId2(), conv.getLastUpdate(),
				conv.getConversationKey());

		System.out.println(query);
		this.executeQuery(query);

	}

	public void deleteConversationByIds(int id1, int id2) throws Exception {

		String query = String.format("DELETE FROM %s WHERE %s = '%s' AND %s = '%s'", TABLE_CONVERSATIONS,
				FIELD_CONVERSATIONS_ID1, id1, FIELD_CONVERSATIONS_ID2, id2);

		System.out.println(query);
		this.executeQuery(query);

	}

	public void updateConversation(Conversation conv) throws Exception {

		String query = String.format("UPDATE %s SET %s = '%s' WHERE %s = '%s' AND %s = '%s';", TABLE_CONVERSATIONS,
				FIELD_CONVERSATIONS_LAST_UPDATE, conv.getLastUpdate(), FIELD_CONVERSATIONS_ID1, conv.getId1(),
				FIELD_CONVERSATIONS_ID2, conv.getId2());

		System.out.println(query);
		this.executeQuery(query);

	}

	public Conversation getConversationByIds(int id1, int id2) throws Exception {

		String query = String.format("SELECT * FROM %s WHERE (%s = '%s' AND %s = '%s') OR (%s = '%s' AND %s = '%s')",
				TABLE_CONVERSATIONS, FIELD_CONVERSATIONS_ID1, id1, FIELD_CONVERSATIONS_ID2, id2,
				FIELD_CONVERSATIONS_ID1, id2, FIELD_CONVERSATIONS_ID2, id1);
		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		Conversation c = new Conversation();

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				int id = resultSet.getInt(FIELD_CONVERSATIONS_ID);
				long lastUpdate = resultSet.getLong(FIELD_CONVERSATIONS_LAST_UPDATE);
				String conversationKey = resultSet.getString(FIELD_CONVERSATIONS_CONVERSATIONKEY);
				c.setId(id);
				c.setId1(id1);
				c.setId2(id2);
				c.setLastUpdate(lastUpdate);
				c.setConversationKey(conversationKey);
			}

		} catch (Exception e) {

			throw e;

		} finally {
			closeQuietly(connection);
		}

		return c;

	}
	
	public List<Conversation> getActiveConversationsByID(int accountID) throws Exception {

		List<Conversation> activeConversations = new ArrayList<Conversation>();

		String query = String.format("SELECT * FROM %s WHERE %s = '%s' OR %s = '%s' ORDER BY %s DESC", TABLE_CONVERSATIONS,
				FIELD_CONVERSATIONS_ID1, accountID, FIELD_CONVERSATIONS_ID2, accountID, FIELD_CONVERSATIONS_LAST_UPDATE);

		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			Conversation c = new Conversation();

			while (resultSet.next()) {
				int id = resultSet.getInt(FIELD_CONVERSATIONS_ID);
				int id1 = resultSet.getInt(FIELD_CONVERSATIONS_ID1);
				int id2 = resultSet.getInt(FIELD_CONVERSATIONS_ID2);
				Long lastUpdate = resultSet.getLong(FIELD_CONVERSATIONS_LAST_UPDATE);
				String conversationKey = "";
				c = new Conversation(id, id1, id2, lastUpdate, conversationKey);
				activeConversations.add(c);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}

		return activeConversations;

	}
	
	public List<Conversation> getActiveConversationsForID(int accountID, int limit, int offSet) throws Exception {
			
		List<Conversation> activeConversations = new ArrayList<Conversation>();
		
		String query = String.format("SELECT * FROM %s WHERE %s = '%s' OR %s = '%s' ORDER BY %s DESC LIMIT %s OFFSET %s;", TABLE_CONVERSATIONS,
				FIELD_CONVERSATIONS_ID1, accountID, FIELD_CONVERSATIONS_ID2, accountID, FIELD_CONVERSATIONS_LAST_UPDATE, limit, offSet);
		
		System.out.println(query);
		
		Statement statement;
		ResultSet resultSet;
		
		try {
			
			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			Conversation c = new Conversation();
			
			while(resultSet.next()) {
				
				int id = resultSet.getInt(FIELD_CONVERSATIONS_ID);
				int id1 = resultSet.getInt(FIELD_CONVERSATIONS_ID1);
				int id2 = resultSet.getInt(FIELD_CONVERSATIONS_ID2);
				Long lastUpdate = resultSet.getLong(FIELD_CONVERSATIONS_LAST_UPDATE);
				String conversationKey = "";
				c = new Conversation(id, id1, id2, lastUpdate, conversationKey);
				activeConversations.add(c);
				
			}
			
		} catch (Exception e) {
			
			throw e;
			
		} finally {
			
			closeQuietly(connection);
			
		}
		
		return activeConversations;
			
	}

	// Messages

	public void insertMessage(Message message) throws Exception {

		String query = String.format("INSERT INTO %s(%s, %s, %s, %s, %s) VALUES( '%s', '%s', '%s', '%s', '%s');",
				TABLE_MESSAGES, FIELD_MESSAGES_ID_CONV, FIELD_MESSAGES_ID_SENDER, FIELD_MESSAGES_ID_RECEIVER,
				FIELD_MESSAGES_DATETIME, FIELD_MESSAGES_CONTENT, message.getIdConv(), message.getIdSender(),
				message.getIdReceiver(), message.getTime(), message.getContent());

		System.out.println(query);
		this.executeQuery(query);

	}

	public void deleteMessage(int idMessage) throws Exception {

		String query = String.format("DELETE FROM %s WHERE %s = '%s'", TABLE_MESSAGES, FIELD_MESSAGES_ID, idMessage);

		System.out.println(query);
		this.executeQuery(query);

	}

	public List<Message> getLatestMessages(int idConversation, int accountID, int limit, int offSet) throws Exception {

		List<Message> latestMessages = new ArrayList<Message>();

		String query = String.format(
				"SELECT * FROM %s WHERE %s = '%s' AND (%s = '%s' OR %s = '%s') ORDER BY %s DESC LIMIT %s OFFSET %s;",
				TABLE_MESSAGES, FIELD_MESSAGES_ID_CONV, idConversation, FIELD_MESSAGES_ID_SENDER, accountID,
				FIELD_MESSAGES_ID_RECEIVER, accountID, FIELD_MESSAGES_DATETIME, limit, offSet);
		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			Message m = new Message();

			while (resultSet.next()) {
				int id = resultSet.getInt(FIELD_MESSAGES_ID);
				int idConv = resultSet.getInt(FIELD_MESSAGES_ID_CONV);
				int idSender = resultSet.getInt(FIELD_MESSAGES_ID_SENDER);
				int idReciver = resultSet.getInt(FIELD_MESSAGES_ID_RECEIVER);
				Long lastUpdate = resultSet.getLong(FIELD_MESSAGES_DATETIME);
				String content = resultSet.getString(FIELD_MESSAGES_CONTENT);
				m = new Message(id, idConv, idSender, idReciver, lastUpdate, content);
				latestMessages.add(m);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			closeQuietly(connection);
		}

		return latestMessages;

	}

	// Sessions

	public void insertSession(Session session) throws Exception {

		String query = String.format("INSERT INTO %s(%s, %s, %s) VALUES( '%s', '%s', '%s');", TABLE_SESSIONS,
				FIELD_SESSIONS_ID, FIELD_SESSIONS_CREATION_DATE, FIELD_SESSIONS_EXPIRATION_DATE, session.getId(),
				session.getCreationDate(), session.getExpirationDate());

		System.out.println(query);
		this.executeQuery(query);

	}
	
	public void updateSession(Session session) throws Exception {
		
		String query = String.format("UPDATE %s SET %s = '%s', %s = '%s' WHERE %s = '%s';", TABLE_SESSIONS,
				FIELD_SESSIONS_CREATION_DATE, session.getCreationDate(), FIELD_SESSIONS_EXPIRATION_DATE, session.getExpirationDate(),
				FIELD_SESSIONS_ID, session.getId());

		System.out.println(query);
		this.executeQuery(query);
		
	}

	public Session getSessionById(String sessionId) throws Exception {

		String query = "SELECT * FROM " + TABLE_SESSIONS + " WHERE " + FIELD_SESSIONS_ID + " = '" + sessionId + "';";
		System.out.println(query);

		Statement statement;
		ResultSet resultSet;

		Session s = new Session();

		try {

			connect();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String id = resultSet.getString(FIELD_SESSIONS_ID);
				long creationDate = resultSet.getLong(FIELD_SESSIONS_CREATION_DATE);
				long expirationDate = resultSet.getLong(FIELD_SESSIONS_EXPIRATION_DATE);
				s.setId(id);
				s.setCreationDate(creationDate);
				s.setExpirationDate(expirationDate);

			}

		} catch (Exception e) {

			throw e;

		} finally {

			closeQuietly(connection);

		}

		return s;
	}

}
