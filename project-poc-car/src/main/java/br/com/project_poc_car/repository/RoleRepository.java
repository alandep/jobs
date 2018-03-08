package br.com.project_poc_car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.project_poc_car.domain.Role;

/**
 * @author alan.franco
 *
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

	/**
	 * @param role
	 * @return
	 */
	public Role findByRole(String role);
}
