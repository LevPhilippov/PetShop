package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecs {

    public static Specification<Product> priceLessThanOrEq(BigDecimal value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
    }

    public static Specification<Product> priceGreaterThanOrEq(BigDecimal value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
    }

    public static Specification<Product> wordLike(String word) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), word);
    }
}
