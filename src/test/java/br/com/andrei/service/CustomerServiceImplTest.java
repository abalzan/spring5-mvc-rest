package br.com.andrei.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.andrei.api.v1.mapper.CustomerMapper;
import br.com.andrei.api.v1.model.CustomerDTO;
import br.com.andrei.domain.Customer;
import br.com.andrei.repository.CustomerRepository;

public class CustomerServiceImplTest {

	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";

	@Mock
	CustomerRepository customerRepository;

	CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	CustomerService customerService;

	@Before
	    public void setUp() throws Exception {
	        MockitoAnnotations.initMocks(this);

	        customerService = new CustomerServiceImpl(customerMapper, customerRepository);
	}

	@Test
	public void testGetAllCustomers() {
		List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

		when(customerRepository.findAll()).thenReturn(customers);

		List<CustomerDTO> categoriesDTO = customerService.getAllCustomers();

		assertEquals(3, categoriesDTO.size());
	}

	@Test
	public void testGetCustomerById() {
		CustomerDTO customer = new CustomerDTO();
		customer.setId(1L);
		customer.setFirstname(FIRST_NAME);
		customer.setLastname(LAST_NAME);
		customer.setCustomerURL("/api/v1/customer/1");

		when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(customer);

		CustomerDTO customerDTO = customerService.getCustomerById(1L);

		assertEquals(FIRST_NAME, customerDTO.getFirstname());
		assertEquals(LAST_NAME, customerDTO.getLastname());
	}

	@Test
	public void testCreateNewCustomer() {
		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRST_NAME);

		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(customerDTO.getFirstname());
		savedCustomer.setLastname(customerDTO.getLastname());
		savedCustomer.setId(1l);

		when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(savedCustomer);

		// when
		CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

		// then
		assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
		assertEquals("/api/v1/customer/1", savedDto.getCustomerURL());
	}

	@Test
	public void testSaveCustomerByDTO() throws Exception {

		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRST_NAME);

		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(customerDTO.getFirstname());
		savedCustomer.setLastname(customerDTO.getLastname());
		savedCustomer.setId(1l);

		when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(savedCustomer);

		// when
		CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

		// then
		assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
		assertEquals("/api/v1/customer/1", savedDto.getCustomerURL());
	}

}
