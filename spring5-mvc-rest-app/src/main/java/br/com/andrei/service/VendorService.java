package br.com.andrei.service;

import java.util.List;

import br.com.andrei.model.VendorDTO;;

public interface VendorService {

	List<VendorDTO> getAllVendors();
	
	VendorDTO getVendorById(Long id);
	
	VendorDTO createNewVendor(VendorDTO VendorDTO);
	
	VendorDTO saveVendorByDTO(Long id, VendorDTO VendorDTO);

	VendorDTO patchVendor(Long id, VendorDTO VendorDTO);
	
	void deleteVendorById(Long id);
}
