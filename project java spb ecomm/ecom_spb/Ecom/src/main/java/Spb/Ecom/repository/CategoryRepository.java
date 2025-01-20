package Spb.Ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Spb.Ecom.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Optional<Category> findByCategoryName(String categoryName);

}
