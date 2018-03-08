package br.com.project_poc_car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.project_poc_car.domain.User;

/**
 * @author alan.franco
 *
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * @param email
	 * @return
	 */
	User findByUser(String email);
}
