package br.com.andrei.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import br.com.andrei.api.v1.mapper.CustomerMapper;
import br.com.andrei.domain.Customer;
import br.com.andrei.model.CustomerDTO;
import br.com.andrei.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
		this.customerMapper = customerMapper;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream().map(customer -> {
			CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
			customerDTO.setCustomerUrl(getCustomerURL(customer.getId()));
			return customerDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO)
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

		return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
	}

	private CustomerDTO saveAndReturnDTO(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);

		CustomerDTO returnCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

		returnCustomerDTO.setCustomerUrl(getCustomerURL(savedCustomer.getId()));

		return returnCustomerDTO;
	}

	@Override
	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		customer.setId(id);
		
		return saveAndReturnDTO(customer);
	}
	
	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).map(
				customer -> {
					if(StringUtils.isNotBlank(customerDTO.getFirstname())) {
						customer.setFirstname(customerDTO.getFirstname());
					}
					if(StringUtils.isNotBlank(customerDTO.getLastname())) {
						customer.setLastname(customerDTO.getLastname());
					}
					
					CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
					
					returnDTO.setCustomerUrl(getCustomerURL(id));
					
					return returnDTO;
				}).orElseThrow(ResourceNotFoundException::new);
	}

	private String getCustomerURL(Long id) {
		return "/api/v1/customer/" + id;
	}

	@Override
	public void deleteCustomerById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent())
			customerRepository.deleteById(id);
		
	}
}
