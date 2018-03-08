package br.com.project_poc_car.service.fachada;

import br.com.project_poc_car.domain.User;

/**
 * @author alan.franco
 *
 */
public interface UserService {

	public User findUserByEmail(String email);

	public void saveUser(User user);
}
