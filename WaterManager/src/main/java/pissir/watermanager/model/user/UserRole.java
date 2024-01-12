package pissir.watermanager.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Alessandro Gattico
 */
@Getter
@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {
	GESTOREIDRICO,
	GESTOREAZIENDA,
	SYSTEMADMIN;
	
	
	@Override
	public String getAuthority() {
		return this.name();
	}
}
