package lev.philippov.originmvc.services.mappers;

import lev.philippov.originmvc.domain.product.structure.Product;
import lev.philippov.originmvc.repositories.CategoryRepository;
import lev.philippov.originmvc.web.models.ProductDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class ProductMapperDecorator implements ProductMapper {

    private AttributeMapper attributeMapper;
    private CategoryRepository categoryRepository;
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Autowired
    public void setAttributeMapper(AttributeMapper attributeMapper) {
        this.attributeMapper = attributeMapper;
    }

    @Override
    public ProductDto productToProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setVersion(product.getVersion());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setAttributes(product.getAttributes().stream().map(attributeMapper::attributeToAttributeDto).collect(Collectors.toList()));
        dto.setCategory(product.getCategory().getTitle());
        dto.setUpc(product.getUpc());
        return dto;
    }

    @Override
    public Product productDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setVersion(productDto.getVersion());
        product.setCreatedAt(productDto.getCreatedAt());
        product.setUpdatedAt(productDto.getUpdatedAt());
        product.setAttributes(productDto.getAttributes().stream().map(attributeMapper::attributeDtoToAttribute).collect(Collectors.toList()));
        product.setCategory(categoryRepository.getCategoryByTitle(productDto.getCategory()));
        product.setUpc(productDto.getUpc());
        return product;
    }
}
