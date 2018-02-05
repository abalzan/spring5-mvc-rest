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
import org.springframework.boot.test.context.SpringBootTest;

import br.com.andrei.api.v1.mapper.CategoryMapper;
import br.com.andrei.model.CategoryDTO;
import br.com.andrei.domain.Category;
import br.com.andrei.repository.CategoryRepository;

@SpringBootTest
public class CategoryServiceImplTest {

	private static final String NAME = "John";

	CategoryService categoryService;
	
	@Mock
	CategoryRepository categoryRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
	}

	@Test
	public void getAllCategoriesTest() {
		
		List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
		
		when(categoryRepository.findAll()).thenReturn(categories);
		
		List<CategoryDTO> categoriesDTO = categoryService.getAllCategories();
		
		assertEquals(3, categoriesDTO.size());
		
	}
	
	@Test
	public void getCategoryByNameTest() {

		Category category = new Category();
		category.setId(1L);
		category.setName(NAME);
		
		when(categoryRepository.findByName(Mockito.anyString())).thenReturn(category);
		
		CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);
		
		assertEquals(NAME, categoryDTO.getName());
		assertEquals(Long.valueOf(1L), categoryDTO.getId());
		
	}

}
