package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.product.structure.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class ProductSpecs {

    public static Specification<Product> priceLessThanOrEq(BigDecimal value) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
            }
        };
    }

    public static Specification<Product> priceGreaterThanOrEq(BigDecimal value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
    }

    public static Specification<Product> wordLike(String word) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), word);
    }
}
