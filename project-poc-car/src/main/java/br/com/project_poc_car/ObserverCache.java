package br.com.project_poc_car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.project_poc_car.service.CarService;

/**
 * @author alan.franco
 *
 *         Esta classe tem como requisito observar todos os caches e
 *         atualiza-los de tempos em tempos
 */
@Configuration
@EnableScheduling
public class ObserverCache {

	@Autowired
	private CarService carService;

	@Autowired
	private CacheManager cacheManager;

	/**
	 * O Método abaixo vai atualizar o cache de 10 em 10 segundos
	 */
	@Scheduled(fixedDelay = 10000)
	public void IaaSStatusRefresh() {
		Cache countries = this.cacheManager.getCache("cars");
		countries.clear();
		carService.findAllCars();
	}

}