package pissir.watermanager.security.services;

import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import pissir.watermanager.model.user.UserProfile;
import pissir.watermanager.model.user.UserRole;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Service
public class TokenService {
	
	private final JwtEncoder jwtEncoder;
	
	private final JwtDecoder jwtDecoder;
	
	
	public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
	}
	
	
	public String generateJwt(UserProfile user) {
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
	
	
	public boolean validateTokenAndRole(String token, UserRole requiredRole) {
		try {
			Jwt jwt = jwtDecoder.decode(token);
			List<String> roles = jwt.getClaimAsStringList("roles");
			
			return roles.contains(requiredRole.toString());
		} catch (JwtException e) {
			return false;
		}
	}
	
}
