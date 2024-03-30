package pissir.watermanager.security.repository;

import org.springframework.stereotype.Repository;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.user.UserProfile;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */


@Repository
public class UserRepository {
	
	private final DAO dao;
	
	
	public UserRepository(DAO dao) {
		this.dao = dao;
	}
	
	
	public UserProfile findByUsername(String username) {
		return this.dao.getUserByUsername(username);
	}
	
	
	public void saveUser(UserProfile user) {
		this.dao.addUser(user);
	}
	
}

