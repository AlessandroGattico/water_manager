package pissir.watermanager.security.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Getter
@Setter
@Component
public class RsaKeyProperties {
	
	private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey;
	
	
	public RsaKeyProperties () {
		KeyPair pair = KeyGeneratorUtility.generateKey();
		
		this.publicKey = (RSAPublicKey) pair.getPublic();
		this.privateKey = (RSAPrivateKey) pair.getPrivate();
	}
	
}
