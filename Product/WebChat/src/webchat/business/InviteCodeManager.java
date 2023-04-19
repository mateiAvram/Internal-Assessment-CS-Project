package webchat.business;

import java.util.List;

import webchat.data.SqlDataService;
import webchat.model.InviteCode;
import webchat.model.isValidInvCodeResp;

public class InviteCodeManager {

	public InviteCodeManager() {
		
	}
	
	public void insertInviteCode(InviteCode newInviteCode) throws Exception{
		
		SqlDataService dataService = new SqlDataService();
		dataService.insertInviteCode(newInviteCode);
		
	}
	
	public void deleteInviteCodeByCode(String inviteCode) throws Exception {
		SqlDataService dataService = new SqlDataService();
		dataService.deleteInviteCode(inviteCode);
	}
	
	public isValidInvCodeResp isValidInviteCode(String inviteCode) throws Exception{
		isValidInvCodeResp resp = new isValidInvCodeResp();
		resp.setValid(false);
		SqlDataService dataService = new SqlDataService();
		int id = dataService.getIdAccountInviteCode(inviteCode);
		if(id > -1) {
			resp.setValid(true);
			resp.setIdAccount(id);
		}
		return resp;
	}
	
	public List<String> getInviteCodesById(int id) throws Exception {
		
		SqlDataService dataService = new SqlDataService();
		List<String> list = dataService.getInviteCodesById(id);
		return list;
	}
	
}
