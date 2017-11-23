package br.com.andrei.controller.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.andrei.api.v1.model.CustomerDTO;
import br.com.andrei.controller.RestResponseEntityExceptionHandler;
import br.com.andrei.service.CustomerService;
import br.com.andrei.service.ResourceNotFoundException;

public class CustomerControllerTest extends AbstractRestControllerTest{

	private static final String LAST_NAME = "Doe";
	private static final String FIRST_NAME = "John";
	private static final String URL = "/api/v1/customers";

	@Mock
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	@Test
	public void testGetAllCustomers() throws Exception {

		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname(FIRST_NAME);
		customer1.setLastname(LAST_NAME);
		customer1.setCustomerURL(URL);
		
		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstname("Joana");
		customer2.setLastname("Does");
		
		List<CustomerDTO> customers = Arrays.asList(customer1, customer2);
		
		when(customerService.getAllCustomers()).thenReturn(customers);
		
		mockMvc.perform(get("/api/v1/customers")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
		
	}

	@Test
	public void testGetCustomerByFirstname() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname(FIRST_NAME);
		customer.setLastname(LAST_NAME);
		
		when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(customer);
		
		mockMvc.perform(get("/api/v1/customers/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)));
	}
	
	@Test
	public void testCreateNewCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRST_NAME);
		customerDTO.setLastname(LAST_NAME);
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customerDTO.getFirstname());
		returnDTO.setLastname(customerDTO.getLastname());
		returnDTO.setCustomerURL("/api/v1/customers/1");
		
		when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);
		
		mockMvc.perform(post("/api/v1/customers")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(customerDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
				.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("John");
		customerDTO.setLastname("Doe");
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customerDTO.getFirstname());
		returnDTO.setLastname(customerDTO.getLastname());
		returnDTO.setCustomerURL("/api/v1/customers/1");
	
		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
		
		mockMvc.perform(put("/api/v1/customers/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(customerDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
				.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
		
	}	
	
	  @Test
	    public void testPatchCustomer() throws Exception {

	        //given
	        CustomerDTO customer = new CustomerDTO();
	        customer.setFirstname(FIRST_NAME);

	        CustomerDTO returnDTO = new CustomerDTO();
	        returnDTO.setFirstname(customer.getFirstname());
	        returnDTO.setLastname(LAST_NAME);
	        returnDTO.setCustomerURL(CustomerController.BASE_URL + "/1");

	        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

	        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(customer)))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
	                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
	                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	    }

	    @Test
	    public void testDeleteCustomer() throws Exception {

	        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());

	        verify(customerService).deleteCustomerById(anyLong());
	    }

	    @Test
	    public void testNotFoundException() throws Exception {

	        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

	        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNotFound());
	    }
}
