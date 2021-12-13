package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.Product;
import lev.philippov.originmvc.domain.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Set<ProductDto> findAllByIdIn(Collection<Long> ids);
}
