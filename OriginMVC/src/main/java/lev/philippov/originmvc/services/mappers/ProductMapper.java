package lev.philippov.originmvc.services.mappers;

import lev.philippov.originmvc.domain.product.structure.Product;
import lev.philippov.originmvc.web.models.ProductDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AttributeMapper.class, ParamMapper.class})
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    @Mapping(source = "category.title", target = "category")
    ProductDto productToProductDto(Product product);

    @InheritInverseConfiguration
    Product productDtoToProduct(ProductDto productDto);


}
