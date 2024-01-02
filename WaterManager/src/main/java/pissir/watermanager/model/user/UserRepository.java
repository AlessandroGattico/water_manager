package pissir.watermanager.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<UserProfile, Integer> {

	Optional<UserProfile> findByEmail(String email);

}
