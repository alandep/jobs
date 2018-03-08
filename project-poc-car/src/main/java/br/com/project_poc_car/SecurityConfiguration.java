package br.com.project_poc_car;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		try {
			auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
					.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
		} catch (Exception e) {
			logger.error("Ocorreu um erro ao executar as consultas de autenticação: " + e.getMessage());
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		try {
//			http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/project-poc-car/login").permitAll()
//					.antMatchers("/project-poc-car/register-login").permitAll().antMatchers("/project-poc-car/pages**")
//					.hasAuthority("ADMIN").anyRequest().authenticated().and().csrf().disable().formLogin()
//					.loginPage("/project-poc-car/pages/login.html").failureUrl("/project-poc-car/login?error=true")
//					.defaultSuccessUrl("/project-poc-car").usernameParameter("user").passwordParameter("password").and()
//					.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
//					.exceptionHandling().accessDeniedPage("/access-denied");
			
			http.authorizeRequests().antMatchers("/project-poc-car/pages/login.html").permitAll()
			.antMatchers("/project-poc-car/pages/register-login.html").permitAll().antMatchers("/project-poc-car/pages**")
			.hasAuthority("ADMIN").anyRequest().authenticated().and().csrf().disable().formLogin()
			.loginPage("/project-poc-car/pages/login.html").failureUrl("/project-poc-car/login?error=true")
			.defaultSuccessUrl("/project-poc-car").usernameParameter("user").passwordParameter("password").and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
			.exceptionHandling().accessDeniedPage("/access-denied");

		} catch (Exception e) {
			logger.error("Ocorreu um erro ao executar as consultas de autenticação: " + e.getMessage());
		}
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/control/**",
				"/fonts/**", "/font-awesome/**");
	}

}