package pissir.watermanager.security.services;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import pissir.watermanager.model.user.UserProfile;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {
	
	private final JwtEncoder jwtEncoder;
	
	private final JwtDecoder jwtDecoder;
	
	
	public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
	}
	
	
	public String generateJwt(UserProfile user){
		Instant now = Instant.now();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.subject(user.getUsername())
				.claim("roles", user.getRole())
				.expiresAt(now.plus(Duration.ofHours(1)))
				.build();
		
		
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
}
