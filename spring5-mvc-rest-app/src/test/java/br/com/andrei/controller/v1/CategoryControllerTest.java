package br.com.andrei.controller.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.andrei.api.v1.model.CategoryDTO;
import br.com.andrei.controller.RestResponseEntityExceptionHandler;
import br.com.andrei.service.CategoryService;
import br.com.andrei.service.ResourceNotFoundException;

@SpringBootTest
public class CategoryControllerTest {

	@Mock
	CategoryService categoryService;
	
	@InjectMocks
	CategoryController categoryController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	@Test
	public void testGetAllCategories() throws Exception {

		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1L);
		category1.setName("Name1");
		
		CategoryDTO category2 = new CategoryDTO();
		category2.setId(2L);
		category2.setName("Name2");
		
		List<CategoryDTO> categories = Arrays.asList(category1, category2);
		
		when(categoryService.getAllCategories()).thenReturn(categories);
		
		mockMvc.perform(get("/api/v1/categories")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categories", hasSize(2)));
		
	}

	@Test
	public void testGetCategoryByName() throws Exception {
		CategoryDTO category = new CategoryDTO();
		category.setId(1L);
		category.setName("Name1");
		
		when(categoryService.getCategoryByName(Mockito.anyString())).thenReturn(category);
		
		mockMvc.perform(get("/api/v1/categories/Jim")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo("Name1")));
	}
	
    @Test
    public void testGetByNameNotFound() throws Exception {

        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CategoryController.BASE_URL + "/Foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}