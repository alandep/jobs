//package br.com.project_poc_car.automasite.test.controller;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import br.com.project_poc_car.ApplicationContextConfig;
//import br.com.project_poc_car.controller.CarController;
//import br.com.project_poc_car.domain.Car;
//import br.com.project_poc_car.service.CarService;
//
///**
// * @author alan.franco
// *
// */
//// @WebAppConfiguration
//// @RunWith(SpringJUnit4ClassRunner.class)
//// @ContextConfiguration(classes = { ApplicationContextConfig.class })
//
//// @RunWith(SpringRunner.class)
//// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//// @AutoConfigureMockMvc
//@RunWith(MockitoJUnitRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = ApplicationContextConfig.class)
//public class CarControllerUnitTest {
//
//	private MockMvc mockMvc;
//
//	@Mock
//	private CarService carService;
//
//	@InjectMocks
//	private CarController carController;
//
//	@Autowired
//	private WebApplicationContext wac;
//
//	@Before
//	public void init() {
//		MockitoAnnotations.initMocks(this);
//		// mockMvc = MockMvcBuilders.standaloneSetup(carController).addFilters(new
//		// CORSFilter()).build();
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//	}
//
//	// ========= Teste para recuperar todos os carros
//
//	@Test
//	public void testGetAllSuccess() throws Exception {
//		List<Car> cars = Arrays.asList(
//				new Car(1, "ford", "ka", "branco", 2018, 20000.26, "Bom também", false, new Date(), new Date()),
//				new Car(2, "Fiat", "uno", "rosa", 2018, 35000.26, "Eta carrinho bom", true, new Date(), new Date()));
//
//		when(carService.findAllCars()).thenReturn(cars);
//
//		mockMvc.perform(get("/car/list-all-cars")).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
//				.andExpect(jsonPath("$[0].brand", is("ka"))).andExpect(jsonPath("$[1].id", is(2)))
//				.andExpect(jsonPath("$[1].brand", is("Uno")));
//
//		verify(carService, times(1)).findAllCars();
//		verifyNoMoreInteractions(carService);
//	}
//
//	// =========================================== Get Car By ID
//
//	@Test
//	public void test_get_by_id_success() throws Exception {
//		Car user = new Car(1, "Daenerys Targaryen");
//
//		when(carService.findById(1)).thenReturn(user);
//
//		mockMvc.perform(get("/users/{id}", 1)).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.username", is("Daenerys Targaryen")));
//
//		verify(carService, times(1)).findById(1);
//		verifyNoMoreInteractions(carService);
//	}
//
//	@Test
//	public void test_get_by_id_fail_404_not_found() throws Exception {
//		when(carService.findById(1)).thenReturn(null);
//
//		mockMvc.perform(get("/users/{id}", 1)).andExpect(status().isNotFound());
//
//		verify(carService, times(1)).findById(1);
//		verifyNoMoreInteractions(carService);
//	}
//
//	// =========================================== Create New Car
//	// ========================================
//
//	@Test
//	public void test_create_user_success() throws Exception {
//		Car user = new Car("Arya Stark");
//
//		when(carService.exists(user)).thenReturn(false);
//		doNothing().when(carService).create(user);
//
//		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
//				.andExpect(status().isCreated()).andExpect(header().string("location", containsString("/users/0")));
//
//		verify(carService, times(1)).exists(user);
//		verify(carService, times(1)).create(user);
//		verifyNoMoreInteractions(carService);
//	}
//
//	@Test
//	public void test_create_user_fail_404_not_found() throws Exception {
//		Car user = new Car("username exists");
//
//		when(carService.exists(user)).thenReturn(true);
//
//		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
//				.andExpect(status().isConflict());
//
//		verify(carService, times(1)).exists(user);
//		verifyNoMoreInteractions(carService);
//	}
//
//	// =========================================== Update Existing Car
//	// ===================================
//
//	@Test
//	public void test_update_user_success() throws Exception {
//		Car user = new Car(1, "Arya Stark");
//
//		when(carService.findById(user.getId())).thenReturn(user);
//		doNothing().when(carService).update(user);
//
//		mockMvc.perform(
//				put("/users/{id}", user.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
//				.andExpect(status().isOk());
//
//		verify(carService, times(1)).findById(user.getId());
//		verify(carService, times(1)).update(user);
//		verifyNoMoreInteractions(carService);
//	}
//
//	@Test
//	public void test_update_user_fail_404_not_found() throws Exception {
//		Car user = new Car(999, "user not found");
//
//		when(carService.findById(user.getId())).thenReturn(null);
//
//		mockMvc.perform(
//				put("/users/{id}", user.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
//				.andExpect(status().isNotFound());
//
//		verify(carService, times(1)).findById(user.getId());
//		verifyNoMoreInteractions(carService);
//	}
//
//	// =========================================== Delete Car
//	// ============================================
//
//	@Test
//	public void test_delete_user_success() throws Exception {
//		Car user = new Car(1, "Arya Stark");
//
//		when(carService.findById(user.getId())).thenReturn(user);
//		doNothing().when(carService).delete(user.getId());
//
//		mockMvc.perform(delete("/users/{id}", user.getId())).andExpect(status().isOk());
//
//		verify(carService, times(1)).findById(user.getId());
//		verify(carService, times(1)).delete(user.getId());
//		verifyNoMoreInteractions(carService);
//	}
//
//	@Test
//	 public void test_delete_user_fail_404_not_found() throws Exception {
//	 Car car = new Car(1, "ford", "ka", "rosa", 2018, 35000.26, "Eta carrinho
//	 bom", true, new Date(), new Date());
//	
//	 when(carService.findById(car.getId())).thenReturn(null);
//	
//	 mockMvc.perform(delete("/users/{id}",
//	 car.getId())).andExpect(status().isNotFound());
//	
//	 verify(carService, times(1)).findById(car.getId());
//	 verifyNoMoreInteractions(carService);
//	 }
//
//	// =========================================== CORS Headers
//	// ===========================================
//
//	@Test
//	public void test_cors_headers() throws Exception {
//		mockMvc.perform(get("/users")).andExpect(header().string("Access-Control-Allow-Origin", "*"))
//				.andExpect(header().string("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"))
//				.andExpect(header().string("Access-Control-Allow-Headers", "*"))
//				.andExpect(header().string("Access-Control-Max-Age", "3600"));
//	}
//
//	public static String asJsonString(final Object obj) {
//		try {
//			final ObjectMapper mapper = new ObjectMapper();
//			return mapper.writeValueAsString(obj);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//}
