package lev.philippov.originmvc.services.mappers;

import lev.philippov.originmvc.domain.product.structure.Attribute;
import lev.philippov.originmvc.domain.product.structure.Inventory;
import lev.philippov.originmvc.domain.product.structure.Product;
import lev.philippov.originmvc.repositories.CategoryRepository;
import lev.philippov.originmvc.web.models.AttributeDto;
import lev.philippov.originmvc.web.models.ProductDto;
import lev.philippov.originmvc.web.models.ShopPageProductDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
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
        dto.setDescription(product.getDescription());
        dto.setVersion(product.getVersion());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setAttributes(product.getAttributes().stream().map(attributeMapper::attributeToAttributeDto).collect(Collectors.toList()));
        dto.setCategory(product.getCategory().getTitle());
        dto.setUpc(product.getUpc());
        dto.setInventory(product.getInventories().stream().mapToInt(Inventory::getQty).sum());
        return dto;
    }

    @Override
    public Product productDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setVersion(productDto.getVersion());
        product.setCreatedAt(productDto.getCreatedAt());
        product.setUpdatedAt(productDto.getUpdatedAt());
        product.setAttributes(productDto.getAttributes().stream().map(attributeDto->{
            Attribute attribute = attributeMapper.attributeDtoToAttribute(attributeDto);
            attribute.setProduct(product);
            return attribute;
        }).collect(Collectors.toList()));
        product.setCategory(categoryRepository.getCategoryByTitle(productDto.getCategory()));
        product.setUpc(productDto.getUpc());
        Inventory inv = new Inventory();
        inv.setQty(productDto.getInventory());
        inv.setProduct(product);
        product.setInventories(List.of(inv));
        return product;
    }

    @Override
    public ShopPageProductDto productToShopPageProductDto(Product product) {
        ShopPageProductDto dto = new ShopPageProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setUpc(product.getUpc());
        dto.setInventory(product.getInventories().stream().mapToInt(Inventory::getQty).sum());
        return dto;
    }
}
