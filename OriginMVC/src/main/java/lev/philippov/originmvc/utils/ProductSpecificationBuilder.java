package lev.philippov.originmvc.utils;

import lev.philippov.originmvc.domain.product.structure.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Map;

import static lev.philippov.originmvc.repositories.ProductSpecs.*;

public class ProductSpecificationBuilder {
    public static final String[] filtersSet = {"minPrice","maxPrice", "word"};

    public static Specification<Product> buildProductSpecification(Map<String, String> params) {
        Specification<Product> ps = Specification.where(null);
        if(params.containsKey(filtersSet[0]))
            ps = ps.and(priceGreaterThanOrEq(new BigDecimal(params.get(filtersSet[0]))));
        if(params.containsKey(filtersSet[1]))
            ps = ps.and(priceLessThanOrEq(new BigDecimal(params.get(filtersSet[1]))));
        if(params.containsKey(filtersSet[2]))
            ps = ps.and(wordLike(params.get(filtersSet[2])));
        return ps;
    }

}
