package pissir.watermanager.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Alessandro Gattico
 */
@Getter
@RequiredArgsConstructor
public enum UserRole {
	/*
	USER(Collections.emptySet()),
	SYSTEMADMIN(
			Set.of(
					ADMIN_READ,
					ADMIN_UPDATE,
					ADMIN_DELETE,
					ADMIN_CREATE,
					GESTOREAZIENDA_READ,
					GESTOREAZIENDA_UPDATE,
					GESTOREAZIENDA_DELETE,
					GESTOREAZIENDA_CREATE,
					GESTOREIDRICO_READ,
					GESTOREIDRICO_UPDATE,
					GESTOREIDRICO_DELETE,
					GESTOREIDRICO_CREATE
			)
	),
	GESTOREAZIENDA(
			Set.of(
					GESTOREAZIENDA_READ,
					GESTOREAZIENDA_UPDATE,
					GESTOREAZIENDA_DELETE,
					GESTOREAZIENDA_CREATE
			)
	),
	GESTOREIDRICO(
			Set.of(
					GESTOREIDRICO_READ,
					GESTOREIDRICO_UPDATE,
					GESTOREIDRICO_DELETE,
					GESTOREIDRICO_CREATE
			)
	);

	private final Set<Permission> permissions;

	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermissions()
				.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}

	 */
	GESTOREIDRICO,
	GESTOREAZIENDA,
	SYSTEMADMIN
	
}
