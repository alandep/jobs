package br.com.project_poc_car.service.fachada;

import java.util.List;

import br.com.project_poc_car.domain.Car;

/**
 * @author alan.franco
 *
 */
public interface CarServiceFacade {

	/**
	 * @param user
	 */
	public void save(Car user);

	/**
	 * @return
	 */
	public List<Car> findAllCars();

	/**
	 * @param car
	 */
	void delete(Car car);
}
