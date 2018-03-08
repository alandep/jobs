package br.com.project_poc_car.automasite.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.project_poc_car.domain.Car;
import br.com.project_poc_car.repository.CarRepository;
import br.com.project_poc_car.service.CarService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BusinessServicesMockTest {

	@Mock
	CarRepository carRepository;

	@InjectMocks
	CarService carService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testfindAllCars() {
		/* Criando objeto avulso para testar métodos get's e set's */
		Car car = new Car();
		car.setId(1);
		car.setBrand("Ford");
		car.setModel("KA");
		car.setColor("Prata/Bari");
		car.setPrice(15000.00);
		car.setRegistrationDate(new Date());
		car.setUpdateDate(new Date());
		car.setIsNew(false);
		car.setDescription("Veículo usado");
		car.setYear(2010);

		when(carRepository.findAll()).thenReturn(Arrays.asList(car, new Car(2, "Subaru", "AW30", "Preto", 2018,
				150000.36, "Carro esportivo", true, new Date(), new Date())));

		assertEquals(2, carService.findAllCars().size());
	}

	// @Test
	// public void testValidationCar() {
	// when(carRepository.retrieveAllData()).thenReturn(new int[] { 15 });
	// assertEquals(15, carService.findTheGreatestFromAllData());
	// }

	@Test
	public void testDelete() {
		Car employee = new Car();
		employee.setBrand("Ford");
		employee.setModel("KA");
		employee.setColor("Prata/Bari");
		employee.setPrice(15000.00);
		employee.setRegistrationDate(new Date());
		employee.setUpdateDate(new Date());
		employee.setIsNew(false);
		employee.setDescription("Veículo usado");
		employee.setYear(2010);
		
		assertNull(employee.getId());
		carService.save(employee);
		System.out.println("Id = " + employee.getId());
		assertNotNull(employee);
		assertEquals((Integer) 10, (Integer) employee.getId());
	}

	@Test
	public void testSave() {
		Car employee = new Car();
		employee.setBrand("Ford");
		employee.setModel("KA");
		employee.setColor("Prata/Bari");
		employee.setPrice(15000.00);
		employee.setRegistrationDate(new Date());
		employee.setUpdateDate(new Date());
		employee.setIsNew(false);
		employee.setDescription("Veículo usado");
		employee.setYear(2010);

		when(carRepository.save(any(Car.class))).thenAnswer(new Answer<Car>() {
			public Car answer(InvocationOnMock invocation) throws Throwable {
				Car employee = (Car) invocation.getArguments()[0];
				employee.setId(10);
				return employee;
			}
		});

		assertNull(employee.getId());
		carService.save(employee);
		System.out.println("Id = " + employee.getId());
		assertNotNull(employee);
		assertEquals((Integer) 10, (Integer) employee.getId());
	}

}