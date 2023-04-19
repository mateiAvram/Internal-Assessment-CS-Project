

package webchat.business;

public class Encrypt {
	
	public Encrypt() {
	}
	
	public String encryptText(String key, String text) {
			
		String encryptedText = "";
		
		String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";
		
		int keyLength = key.length();
		int i = 0;
		
		for (int index = 0; index < text.length(); index++) {
			
			if(i >= keyLength) {
				i = 0;
			}
			
			char textChar = text.charAt(index);
			int charPos = characters.indexOf(textChar);
			
			char encryptedChar;
			
			if(charPos != -1) {
				
				char keyChar = key.charAt(i);
				int posAddChar = characters.indexOf(keyChar);
				int newCharPos = charPos + posAddChar;
				
				if(newCharPos > 62) {
					
					newCharPos = newCharPos - characters.length();
					
				}
				
				encryptedChar = characters.charAt(newCharPos);
				
			} else {
				
				encryptedChar = textChar;
				
			}
			
			i++;
			
			encryptedText = encryptedText + encryptedChar;
			
		}
		
		return encryptedText;
	}
	
}
