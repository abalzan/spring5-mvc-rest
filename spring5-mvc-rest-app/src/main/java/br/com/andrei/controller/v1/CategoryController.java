package br.com.andrei.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.andrei.model.CategoryDTO;
import br.com.andrei.model.CategoryListDTO;
import br.com.andrei.service.CategoryService;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

	static final String BASE_URL = "/api/v1/categories";
	
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CategoryListDTO getAllCategories() {
		CategoryListDTO categoryListDTO = new CategoryListDTO();
		categoryListDTO.getCategories().addAll(categoryService.getAllCategories());
		return categoryListDTO;

	}
	
	@GetMapping("{name}")
	@ResponseStatus(HttpStatus.OK)
	public CategoryDTO getCategotyByName(@PathVariable String name){
		
		return categoryService.getCategoryByName(name);
	}

}
