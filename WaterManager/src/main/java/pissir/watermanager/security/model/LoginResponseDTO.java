package pissir.watermanager.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pissir.watermanager.model.user.UserProfile;

/**
 * @author alessandrogattico
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
	private UserProfile user;
	private String jwt;
}
