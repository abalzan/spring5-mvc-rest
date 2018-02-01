package br.com.andrei.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.andrei.domain.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long>{

	Category findByName(String name);

}
