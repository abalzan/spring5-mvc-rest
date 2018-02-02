package br.com.andrei.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.andrei.domain.Customer;
import br.com.andrei.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
	
	CustomerDTO customerToCustomerDTO(Customer customer);
	
	Customer customerDTOToCustomer(CustomerDTO customerDTO);
}