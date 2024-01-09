package pissir.watermanager.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private Integer id;
	private String authority;
	
	
	public Role () {
	}
	
	
	public Role (String authority) {
		this.authority = authority;
	}
	
	
	public Role (Integer id, String authority) {
		this.id = id;
		this.authority = authority;
	}
	
	
	@Override
	public String getAuthority () {
		return this.authority;
	}
	
	
}
