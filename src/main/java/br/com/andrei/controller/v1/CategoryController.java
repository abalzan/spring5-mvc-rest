package br.com.andrei.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.andrei.api.v1.model.CategoryDTO;
import br.com.andrei.api.v1.model.CategoryListDTO;
import br.com.andrei.service.CategoryService;

@Controller
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

	static final String BASE_URL = "/api/v1/categories";
	
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories() {
		return new ResponseEntity<>(
				new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);

	}
	
	@GetMapping("{name}")
	public ResponseEntity<CategoryDTO> getCategotyByName(@PathVariable String name){
		
		return new ResponseEntity<CategoryDTO>(
				categoryService.getCategoryByName(name), HttpStatus.OK);
	}

}
