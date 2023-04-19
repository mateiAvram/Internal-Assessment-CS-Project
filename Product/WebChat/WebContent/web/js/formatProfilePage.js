function formatProfilePage() {
					
	$("#content").append(
						
			"<div class='bodyPage'><form><div class='form-row'><div class='form-group col-md-10'><input type='text' class='form-control' id='inputUsername'" + 
			"style='border: 3px solid #202020; border-radius: 7px;' placeholder='New Username'><span id='errorUsername' class='invalid-feedback'>Username can only contain" + 
			"lowercase or uppercase characters and digits!</span><span id='errorUsername1' class='invalid-feedback'>Identical Username!</span></div>" + 
			"<div class='form-group col-md-2'><input class='form-control' id='fieldID' style='border: 3px solid #202020; border-radius: 7px;" + 
			"type='text' placeholder='#0000' readonly></div></div>" + 
			"<div class='form-row'><div class='form-group col-md-4'><input type='password' class='form-control' id='inputOldPassword'" + 
			"style='border: 3px solid #202020; border-radius: 7px;' placeholder='Current Password'> <span id='errorOldPassword'" + 
			"class='invalid-feedback'>Incorrect Password</span></div>" + 
			"<div class='form-group col-md-4'><input type='password' class='form-control' id='inputPassword1'" + 
			"style='border: 3px solid #202020; border-radius: 7px;' placeholder='New Password'> <span id='errorPassword1'" + 
			"class='invalid-feedback'>Password must be at least 8 characters long and contain at least 1 lowercase, 1 uppercase and1 digit!</span>" + 
			"<span id='errorPassword2' class='invalid-feedback'>Passwords do not match!</span> <span id='errorPassword3'" + 
			"class='invalid-feedback'>New password can not be the same as the old one!</span></div>" + 
			"<div class='form-group col-md-4'><input type='password' class='form-control' style='border: 3px solid #202020; border-radius: 7px;'" +
			"id='inputPassword2' placeholder='Confirm Password'></div></div>" + 
			"<div class='form-row'><div class='form-group col-md-4'><button type='button' class='btn btn-success' style='width: 100%' " + 
			"id='button-save'>Save</button></div>" + 
			"<div class='form-group col-md-4'><button type='button' class='btn btn-danger' id='button-logOut' style='width: 100%'>Log Out</button>" + 
			"</div>" + 
			"<div class='form-group col-md-4'><button type='button' class='btn btn-danger' id='button-deleteAccount' " + 
			"style='width: 100%'>Delete Account</button></div></div>" +
			"<div class = 'row' style='margin-top: 30px'><table class='table'><thead style='background-color: #202020; color: #f2f2f2'><tr><th style='text-align: center'>Available Invite Codes</th></tr></thead><tbody id='inviteCodes'></tbody></table></div>" + 
			"</div>"
	
	);

}