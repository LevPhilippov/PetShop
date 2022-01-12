package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.product.structure.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category getCategoryByTitle(String title);
}
