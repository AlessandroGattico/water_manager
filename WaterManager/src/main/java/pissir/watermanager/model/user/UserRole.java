package pissir.watermanager.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Alessandro Gattico
 */
@Getter
@RequiredArgsConstructor
public enum UserRole {
	GESTOREIDRICO,
	GESTOREAZIENDA,
	SYSTEMADMIN
	
}
