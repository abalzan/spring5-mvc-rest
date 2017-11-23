package br.com.andrei.service;

import java.util.List;

import br.com.andrei.api.v1.model.CategoryDTO;

public interface CategoryService {

	List<CategoryDTO> getAllCategories();
	
	CategoryDTO getCategoryByName(String name);
}
