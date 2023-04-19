

package webchat.business;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webchat.data.SqlDataService;
import webchat.model.Account;
import webchat.model.AccountResponse;
import webchat.model.Session;

public class AuthenticationManager {

	public AccountResponse logIn(Account accountToVerify, HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws Exception {
		
		AccountResponse resp = new AccountResponse();
		
		try {
			
			AccountManager am = new AccountManager();
			Account databaseAccount = am.getAccountByID(accountToVerify.getId());
			String dataBasePass = databaseAccount.getPassword();
			String dataBaseUser = databaseAccount.getUsername();
			if(!dataBasePass.contentEquals(accountToVerify.getPassword()) || !dataBaseUser.contentEquals(accountToVerify.getUsername())) {
				
				throw new Exception ("Incorrect Username or Password!");
				
			}
			Session s = new Session();
			SessionManager sm = new SessionManager();
			String sessionId = sm.insertSession(s);
			Cookie c = new Cookie("sessionId", sessionId);
			c.setMaxAge(1000*60*30);
			Cookie cAccID = new Cookie("accountID", String.valueOf(accountToVerify.getId()));
			cAccID.setMaxAge(1000*60*30);
			httpResponse.addCookie(c);
			httpResponse.addCookie(cAccID);
			
		} catch (Exception e) {
			
			resp.setErrorByException(e);
		}
		
		return resp;
		
	}
	
	public HttpServletResponse logOut(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		
		Cookie [] cookieList = httpRequest.getCookies();
		
		String cookieSessionID = "";
		
		for(Cookie cookie  : cookieList) {
			
			if(cookie.getName().contentEquals("sessionId")) {
				
				cookieSessionID = cookie.getValue();
				
			}
			
			cookie.setMaxAge(0);
			cookie.setValue(null);
			httpResponse.addCookie(cookie);
			
		}
		
		SqlDataService dataService = new SqlDataService();
		Session s = dataService.getSessionById(cookieSessionID);
		Date dNow = new Date();
		long sExpirationDate = dNow.getTime();
		s.setExpirationDate(sExpirationDate);
		dataService.updateSession(s);
		
		return httpResponse;
		
	}
	
	public boolean isAuth(HttpServletRequest httpRequest) throws Exception {
		
		boolean isAuth = true;
		
		Date d = new Date();
		
		Cookie [] cookieList = httpRequest.getCookies();
		
		if(cookieList == null) {
			
			throw new Exception ("No Cookies");
			
		}
		
		String cookieSessionID = "";
		
		for(Cookie cookie : cookieList) {
			
			if(cookie.getName().contentEquals("sessionId")) {
				
				cookieSessionID = cookie.getValue();
				
			}
			
		}
		
		SqlDataService dataService = new SqlDataService();
		Session s = dataService.getSessionById(cookieSessionID);
		Date sExpirationDate = new Date(s.getExpirationDate());
		
		
		if(d.after(sExpirationDate)) {
			
			isAuth = false;
			
		}
		
		return isAuth;
		
	}
	
}
