package br.com.andrei.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.andrei.domain.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Long>{

}
