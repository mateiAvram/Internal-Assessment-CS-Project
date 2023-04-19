function saveInfo() {
					
	var inputUsername = $("#inputUsername").val();
	
	var usernameHeader = $('b').text();
	
	var inputOldPassword = $("#inputOldPassword").val();
	
	var inputPassword1 = $("#inputPassword1").val();
	
	var inputPassword2 = $("#inputPassword2").val();
	
	var validUsername = true;
	
	var tryPasswordChange = true;
	
	var tryUsernameChange = true;
	
	var validPassword = true;
	
	var same = true;
	
	if(inputUsername == null || inputUsername == "") {
		
		tryUsernameChange = false;
		
	} else {
		
		if (!checkUsername(inputUsername)) {
			$("#errorUsername").show();
			$("#inputUsername").addClass("border-danger");
			validUsername = false;
		} else {
			$("#errorUsername").hide();
			$("#inputUsername").removeClass("border-danger");
			validUsername = true;
		}
		
	}

	if ((inputPassword1 == null || inputPassword1 == "") && (inputPassword2 == null || inputPassword2 == "")) {

		tryPasswordChange = false;

	} else {
		if (!checkPassword(inputPassword1)) {
			$("#errorPassword1").show();
			$("#inputPassword1").addClass("border-danger");
			validPassword = false;
		} else {
			$("#errorPassword1").hide();
			$("#inputPassword1").removeClass("border-danger");
			validPassword = true;
		}
		
		if (inputPassword1 != inputPassword2) {
			$("#errorPassword2").show();
			$("#inputPassword1").addClass("border-danger");
			$("#inputPassword2").addClass("border-danger");
			same = false;
		} else if (!validPassword) {
			$("#errorPassword1").show();
			$("#errorPassword2").hide();
			$("#inputPassword1").addClass("border-danger");
			$("#inputPassword2").addClass("border-danger");
		} else {
			$("#errorPassword2").hide();
			$("#inputPassword1").removeClass("border-danger");
			$("#inputPassword2").removeClass("border-danger");
			same = true;
		}
	}


	var encryptedPass = MD5(inputOldPassword);
	var encryptedNewPass = MD5(inputPassword1);
	var oldUsername = getUsername(usernameHeader);
	var objToSave;
	
	if(!tryUsernameChange && !tryPasswordChange){
		
		$("#errorUsername").hide();
		$("#errorUsername1").hide();
		$("#errorOldPassword").hide();
		$("#errorPassword1").hide();
		$("#errorPassword2").hide();
		$("#errorPassword3").hide();
		$("#inputUsername").removeClass("border-danger");
		$("#inputPassword1").removeClass("border-danger");
		$("#inputPassword2").removeClass("border-danger");
		$("#inputOldPassword").removeClass("border-danger");
		$("#inputUsername").removeClass("border-danger");
		
		$("#saveNoChangesModal").modal('show');
		
	} else if(tryUsernameChange && !tryPasswordChange) {
		
		if(validUsername) {

			if(inputUsername != oldUsername){
				
				$("#errorUsername").hide();
				$("#errorUsername1").hide();
				$("#errorOldPassword").hide();
				$("#errorPassword1").hide();
				$("#errorPassword2").hide();
				$("#errorPassword3").hide();
				$("#inputUsername").removeClass("border-danger");
				$("#inputPassword1").removeClass("border-danger");
				$("#inputPassword2").removeClass("border-danger");
				$("#inputOldPassword").removeClass("border-danger");
				$("#inputUsername").removeClass("border-danger");
				
				objToSave = {
						
						account : {
							
							accountID : -1,
							username : inputUsername,
							password : encryptedPass
							
						},
				
						oldPassword : encryptedPass
						
						
				}
				
			} else {
				
				$("#errorUsername").hide();
				$("#errorUsername1").show();
				$("#errorOldPassword").hide();
				$("#errorPassword1").hide();
				$("#errorPassword2").hide();
				$("#errorPassword3").hide();
				$("#inputUsername").addClass("border-danger");
				$("#inputPassword1").removeClass("border-danger");
				$("#inputPassword2").removeClass("border-danger");
				$("#inputOldPassword").removeClass("border-danger");
				$("#inputUsername").removeClass("border-danger");
				
			}
			
			
			
			
		}
		
		
	} else if(!tryUsernameChange && tryPasswordChange) {
		
		if(validPassword && same){
			
			if(inputPassword1 != inputOldPassword) {
				
				$("#errorUsername").hide();
				$("#errorUsername1").hide();
				$("#errorOldPassword").hide();
				$("#errorPassword1").hide();
				$("#errorPassword2").hide();
				$("#errorPassword3").hide();
				$("#inputUsername").removeClass("border-danger");
				$("#inputPassword1").removeClass("border-danger");
				$("#inputPassword2").removeClass("border-danger");
				$("#inputOldPassword").removeClass("border-danger");
				$("#inputUsername").removeClass("border-danger");
				
				objToSave = {
						
						account : {
							
							accountID : -1,
							username : oldUsername,
							password : encryptedNewPass
							
						},
						
						oldPassword : encryptedPass
						
					}
				
			} else {
				
				$("#errorUsername").hide();
				$("#errorUsername1").hide();
				$("#errorOldPassword").hide();
				$("#errorPassword1").hide();
				$("#errorPassword2").hide();
				$("#errorPassword3").show();
				$("#inputUsername").removeClass("border-danger");
				$("#inputPassword1").addClass("border-danger");
				$("#inputPassword2").addClass("border-danger");
				$("#inputOldPassword").removeClass("border-danger");
				$("#inputUsername").removeClass("border-danger");
				
			}
			
		}
			
		
	} else if(tryUsernameChange && tryPasswordChange) {
		
		if(validUsername && validPassword && same) {
			
			if(inputPassword1 != inputOldPassword) {
				
				if(inputUsername != oldUsername) {
					
					$("#errorUsername").hide();
					$("#errorUsername1").hide();
					$("#errorOldPassword").hide();
					$("#errorPassword1").hide();
					$("#errorPassword2").hide();
					$("#errorPassword3").hide();
					$("#inputUsername").removeClass("border-danger");
					$("#inputPassword1").removeClass("border-danger");
					$("#inputPassword2").removeClass("border-danger");
					$("#inputOldPassword").removeClass("border-danger");
					$("#inputUsername").removeClass("border-danger");
					
					objToSave = {
							
							
							account : {
								
								accountID : -1,
								username : inputUsername,
								password : encryptedNewPass
								
							},
							
							oldPassword : encryptedPass
							
					}
					
				} else {
					
					$("#errorUsername").hide();
					$("#errorUsername1").show();
					$("#errorOldPassword").hide();
					$("#errorPassword1").hide();
					$("#errorPassword2").hide();
					$("#errorPassword3").hide();
					$("#inputUsername").addClass("border-danger");
					$("#inputPassword1").removeClass("border-danger");
					$("#inputPassword2").removeClass("border-danger");
					$("#inputOldPassword").removeClass("border-danger");
					
				}
				
				
				
			} else {
				
				$("#errorUsername").hide();
				$("#errorOldPassword").hide();
				$("#errorPassword1").hide();
				$("#errorPassword2").hide();
				$("#errorPassword3").show();
				$("#inputPassword1").addClass("border-danger");
				$("#inputPassword2").addClass("border-danger");
				$("#inputOldPassword").removeClass("border-danger");
				
				if(inputUsername == oldUsername) {
					
					$("#errorUsername1").show();
					$("#inputUsername").addClass("border-danger");
					
				}
				
			}
			
		} else {
			
			if(inputPassword1 == inputOldPassword) {
				
				$("#errorPassword3").show();
				$("#inputPassword1").addClass("border-danger");
				$("#inputPassword2").addClass("border-danger");
				
			}
			
		}
		
	}
	
	if(!(objToSave == null)) {
		
		$.ajax({
			type : "PUT",
			url : 'http://localhost:8080/WebChat/rest/app/updateAccount',
			dataType : "json",
			contentType : 'application/json',
			data : JSON.stringify(objToSave),
			error : function() {
				//check for connection errors
				alert("Connection to server error.");
			},
			success : function(resp) {
				//check for server errors
				if (resp.errorCode != 0) {
					
					if(resp.errorMessage == "Session has Expired!" || resp.errorMessage == "No Cookies") {
						
						window.location.href = 'logIn.html';
						
					}
					
					$("#errorOldPassword").show();
					$("#inputOldPassword").addClass("border-danger");
				} else {
					$("#errorOldPassword").hide();
					$("#inputOldPassword").removeClass("border-danger");
					$("#saveChangesModal").modal("show");
				}

			}

		}); //End ajax call update account
	
	}
	
}