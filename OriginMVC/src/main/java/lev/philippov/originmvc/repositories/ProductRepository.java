package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.product.structure.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    @Query("select p from Product p join fetch p.category join fetch p.attributes where p.id = ?1")
    Optional<Product> findProductByIdWithAllDetails(UUID uuid);

}
