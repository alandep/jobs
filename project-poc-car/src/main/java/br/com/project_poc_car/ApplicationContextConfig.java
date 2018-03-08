package br.com.project_poc_car;

import java.util.Arrays;
import java.util.Locale;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author alan.franco
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@EnableCaching
@EnableScheduling
@EnableWebSecurity
@ComponentScan("br.com.project_poc_car.*")
@PropertySource("classpath:application.properties")
public class ApplicationContextConfig extends WebMvcConfigurerAdapter {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private final Logger logger = LoggerFactory.getLogger(ApplicationContextConfig.class);

	@Resource
	private Environment env;

	/**
	 * @return
	 */
	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		System.out.println("Create Bean viewResolver");

		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * @return
	 */
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		System.out.println("Create Bean dataSource");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}

	@Bean
	public CacheManager cacheManager() {
		/* Configuração e retorno da implementação do Spring's CacheManager SPI */
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		try {
			cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("default"), new ConcurrentMapCache("cars")));
		} catch (Exception e) {
			this.logger.error("Ocorreu um erro ao criar o bean do CacheManager " + e.getMessage());
		}
		return cacheManager;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		try {
			Locale ptBr = new Locale("pt", "BR");
			slr.setDefaultLocale(ptBr);
		} catch (Exception e) {
			this.logger.error("Ocorreu um erro ao criar o bean do LocaleResolver " + e.getMessage());
		}
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();

		try {
			lci.setParamName("lang");
		} catch (Exception e) {
			this.logger
					.error("Ocorreu um erro ao criar o bean do LocaleChangeInterceptor. Exception: " + e.getMessage());
		}

		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
}