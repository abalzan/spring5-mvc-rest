package br.com.andrei.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.andrei.api.v1.mapper.CustomerMapper;
import br.com.andrei.api.v1.model.CustomerDTO;
import br.com.andrei.domain.Customer;

@SpringBootTest
public class CustomerMapperTest {

	public static final String FIRSTNAME = "Jimmy";
    public static final String LASTNAME = "Fallon";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());

    }
    
    @Test
    public void customerDTOToCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        customerDTO.setCustomerURL("URL");

        //when
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        //then
        assertEquals(FIRSTNAME, customer.getFirstname());
        assertEquals(LASTNAME, customer.getLastname());

    }


}
