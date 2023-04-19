function formatConvPage() {
				
	$(".header").append(
			
		"<button type='button' class='btn profileConversation'></button><b>Name</b>"
		
	);
	
	$("#content").append(
			
		"<div class='chat'><div class='conversation'><div class='messages' style = 'padding-left: 20px; padding-right: 20px'>" + 
		"<div class='container' id='messageList' style= 'padding-left: 15px; padding-right: 15px; padding-top: 10px; padding-bottom: 10px'>" + 
		"</div></div><div class='messageNav'><div class='row'><div class='col-md-11 typeMessage'>" + 
		"<input class='form-control messageBox' id='messageInput'type='text' placeholder='Message'></div>" + 
		"<div class='col-md-1 sendButtonContainer'><button type='button' class='btn sendButton' id='buttonSend'></button></div></div></div></div></div>"
			
	);
	
}