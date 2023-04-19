function sendFriendReq(id2) {
	
	var idUserReceiver = id2;
	
	$.ajax({
		
		type : "POST",
		url : 'http://localhost:8080/WebChat/rest/app/sendFriendReq',
		dataType : "json",
		contentType : 'application/json',
		data : JSON.stringify(idUserReceiver),
		error : function() {
			//check for connection errors
			alert("Connection to server error.");
		},
		success : function(resp) {
			//check for server errors
			if (resp.errorCode != 0) {
				$("#inputAddID").removeClass("border-success");
				$("#successID").hide();
				$("#inputAddID").addClass("border-danger");
				$("#errorID").show();
			} else {
				$("#inputAddID").removeClass("border-danger");
				$("#errorID").hide();
				$("#inputAddID").addClass("border-success");
				$("#successID").show();
			}

		}
		
	})
	
}