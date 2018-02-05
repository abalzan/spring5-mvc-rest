package br.com.andrei.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.andrei.api.v1.mapper.CategoryMapper;
import br.com.andrei.model.CategoryDTO;
import br.com.andrei.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryMapper categoryMapper;
	private final CategoryRepository categoryRepository;
	
	public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
		this.categoryMapper = categoryMapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository
				.findAll()
				.stream()
				.map(category -> categoryMapper.categoryToCategoryDTO(category))
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {
		return categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(name));
	}

}
