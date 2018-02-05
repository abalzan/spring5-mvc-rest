package br.com.andrei.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.andrei.model.CategoryDTO;
import br.com.andrei.domain.Category;

@Mapper
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	//Mapping annotation is necessary when the properties are different.
//	@Mapping(source="id", target="id")
	CategoryDTO categoryToCategoryDTO(Category category);
}
