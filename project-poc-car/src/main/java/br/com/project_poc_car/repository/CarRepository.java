package br.com.project_poc_car.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.project_poc_car.domain.Car;

/**
 * @author alan.franco
 *
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long>, Serializable {

}
