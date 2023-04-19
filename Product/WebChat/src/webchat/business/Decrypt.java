

package webchat.business;

public class Decrypt {
	
	public Decrypt() {
	}
	
	public String decryptText(String key, String text) {
		
		String decryptedText = "";
		
		String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";
		
		int keyLength = key.length();
		int i = 0;
		
		for (int index = 0; index < text.length(); index++) {
			
			if(i >= keyLength) {
				i = 0;
			}
			
			char textChar = text.charAt(index);
			int charPos = characters.indexOf(textChar);
			
			char decryptedChar;
			
			if(charPos != -1) {
				
				char keyChar = key.charAt(i);
				int posSubstractChar = characters.indexOf(keyChar);
				int newCharPos = charPos - posSubstractChar;
				
				if(newCharPos < 0) {
					
					newCharPos = characters.length() + newCharPos;
					
				}
				
				decryptedChar = characters.charAt(newCharPos);
				
			} else {
				
				decryptedChar = textChar;
				
			}
			
			i++;
			
			decryptedText = decryptedText + decryptedChar;
			
		}

		return decryptedText;
	}
	
}
