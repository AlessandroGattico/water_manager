package pissir.watermanager.security.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

public class KeyGeneratorUtility {
	
	public static KeyPair generateKey(){
		
		KeyPair keyPair;
		
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			
			keyPairGenerator.initialize(2048);
			
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception e){
			throw  new IllegalStateException();
		}
		
		return keyPair;
	}
	
}
