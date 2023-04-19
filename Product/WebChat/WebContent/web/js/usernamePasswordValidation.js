function alphanumeric(inputtxt) {
	var letters = /^[0-9a-zA-Z]+$/;
	if (letters.test(inputtxt)) {
		return true;
	} else {
		return false;
	}
}

function checkUsername(inputtxt) {
	var usernameLength = inputtxt.length;
	if (alphanumeric(inputtxt) && usernameLength > 0) {
		return true;
	} else {
		return false;
	}
}

function checkPassword(inputtxt) {
	var decimal = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{7,})/;
	if (inputtxt.match(decimal)) {
		return true;
	} else {
		return false;
	}
}