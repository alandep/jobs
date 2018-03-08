package br.com.project_poc_car.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.project_poc_car.domain.Car;
import br.com.project_poc_car.repository.CarRepository;
import br.com.project_poc_car.service.fachada.CarServiceFacade;

/**
 * @author alan.franco
 *
 */
@Service
@Validated
@Transactional
@CacheConfig(cacheNames = "cars")
public class CarService implements CarServiceFacade {
	private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

	@Autowired
	private CarRepository carRepository;

	@Override
	@Transactional
	public void save(Car car) {
		try {
			validationCar(car);
			carRepository.save(car);
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar o veículo no banco");
		}
	}

	@Cacheable()
	public List<Car> findAllCars() {
		return carRepository.findAll();
	}

	@Override
	@Transactional
	public void delete(Car car) {
		try {
			carRepository.delete(car);
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar o veículo no banco");
		}
	}

	private void validationCar(Car car) {
		if (car.getRegistrationDate() == null) {
			car.setRegistrationDate(new Date());
		}
		
		car.setUpdateDate(new Date());
		
	}
}