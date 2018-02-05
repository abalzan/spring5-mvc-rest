package br.com.andrei.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import br.com.andrei.api.v1.mapper.VendorMapper;
import br.com.andrei.model.VendorDTO;
import br.com.andrei.domain.Vendor;
import br.com.andrei.repository.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

	private final VendorMapper vendorMapper;
	private final VendorRepository vendorRepository;
	
	public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
		this.vendorMapper = vendorMapper;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public List<VendorDTO> getAllVendors() {
		return vendorRepository.findAll().stream().map(vendor -> {
			VendorDTO VendorDTO = vendorMapper.vendorToVendorDTO(vendor);
			VendorDTO.setVendorUrl(getVendorURL(vendor.getId()));
			return VendorDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public VendorDTO getVendorById(Long id) {
		return vendorRepository.findById(id).map(vendorMapper::vendorToVendorDTO)
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorDTO createNewVendor(VendorDTO VendorDTO) {

		return saveAndReturnDTO(vendorMapper.vendorDTOToVendor(VendorDTO));
	}

	private VendorDTO saveAndReturnDTO(Vendor vendor) {
		Vendor savedVendor = vendorRepository.save(vendor);

		VendorDTO returnVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);

		returnVendorDTO.setVendorUrl(getVendorURL(savedVendor.getId()));

		return returnVendorDTO;
	}

	@Override
	public VendorDTO saveVendorByDTO(Long id, VendorDTO VendorDTO) {
		Vendor vendor = vendorMapper.vendorDTOToVendor(VendorDTO);
		vendor.setId(id);
		
		return saveAndReturnDTO(vendor);
	}
	
	@Override
	public VendorDTO patchVendor(Long id, VendorDTO VendorDTO) {
		return vendorRepository.findById(id).map(
				vendor -> {
					if(StringUtils.isNotBlank(VendorDTO.getName())) {
						vendor.setName(VendorDTO.getName());
					}
					
					VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
					
					returnDTO.setVendorUrl(getVendorURL(id));
					
					return returnDTO;
				}).orElseThrow(ResourceNotFoundException::new);
	}

	private String getVendorURL(Long id) {
		return "/api/v1/vendor/" + id;
	}

	@Override
	public void deleteVendorById(Long id) {
		Optional<Vendor> vendor = vendorRepository.findById(id);
		if (vendor.isPresent())
			vendorRepository.deleteById(id);
		
	}
}
