package br.com.andrei.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.andrei.domain.Vendor;

public interface VendorRepository  extends JpaRepository<Vendor, Long>{

	Vendor findByName(String name);

}
