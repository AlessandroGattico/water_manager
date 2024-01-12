package pissir.watermanager.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pissir.watermanager.model.user.UserProfile;
import pissir.watermanager.security.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	private final PasswordEncoder encoder;
	
	private final UserRepository userRepository;
	
	
	public UserService(PasswordEncoder encoder, UserRepository userRepository) {
		this.encoder = encoder;
		this.userRepository = userRepository;
	}
	
	
	public UserProfile loadUserByUsername(String username, String password) throws UsernameNotFoundException {
		System.out.println("In user detail service");
		
		return userRepository.findByUsername(username);
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
	
}
