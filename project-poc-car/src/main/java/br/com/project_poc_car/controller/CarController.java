package br.com.project_poc_car.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.project_poc_car.domain.Car;
import br.com.project_poc_car.service.fachada.CarServiceFacade;

/**
 * @author alan.franco
 *
 */
@RestController
@RequestMapping("/car")
public class CarController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private CarServiceFacade carServiceFacade;

	/**
	 * @return Retorna um JSON com a lista de veículos
	 */
	@RequestMapping(value = "/list-all-cars", method = RequestMethod.GET)
	public String findAllCars() {
		Gson gson = new Gson();
		List<Car> ret = new ArrayList<Car>();
		try {
			ret = this.carServiceFacade.findAllCars();
		} catch (Exception e) {
			logger.error(
					"Ocorreu uma exceção ao selecionar os registros solicitados pelo método findAllCars. Exception: "
							+ e.getMessage());
			return gson.toJson(e.getMessage());
		}
		return gson.toJson(ret);
	}

	/**
	 * @param car
	 * @return Retorna um objeto com estado persistido do banco
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestBody Car car) {
		Gson gson = new Gson();
		try {
			this.carServiceFacade.save(car);
		} catch (Exception e) {
			logger.error("Ocorreu uma exceção ao salvar o registro no banco de dados. Exception: " + e.getMessage());
			return gson
					.toJson("Ocorreu uma exceção ao salvar o registro no banco de dados. Exception: " + e.getMessage());
		}
		return gson.toJson("Registro salvo com sucesso!");
	}

	/**
	 * @param car
	 * @return Retorna um objeto com estado persistido do banco
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestBody Car car) {
		Gson gson = new Gson();
		try {
			this.carServiceFacade.delete(car);
		} catch (Exception e) {
			logger.error("Ocorreu uma exceção ao excluir o registro do banco de dados. Exception: " + e.getMessage());
			return gson.toJson(
					"Ocorreu uma exceção ao excluir o registro no banco de dados. Exception: " + e.getMessage());
		}
		return gson.toJson(car);
	}

}
