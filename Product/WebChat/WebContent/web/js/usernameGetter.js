function getUsername (txt) {
	var finalUsername = txt.substr(0, txt.indexOf("#"));
	return finalUsername;
}