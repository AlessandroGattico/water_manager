package pissir.watermanager.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

	ADMIN_READ("admin:read"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_CREATE("admin:create"),
	ADMIN_DELETE("admin:delete"),
	GESTOREAZIENDA_READ("azienda:read"),
	GESTOREAZIENDA_UPDATE("azienda:update"),
	GESTOREAZIENDA_CREATE("azienda:create"),
	GESTOREAZIENDA_DELETE("azienda:delete"),

	GESTOREIDRICO_READ("idrico:read"),

	GESTOREIDRICO_UPDATE("idrico:update"),

	GESTOREIDRICO_CREATE("idrico:create"),

	GESTOREIDRICO_DELETE("idrico:delete")

	;

	private final String permission;
}
