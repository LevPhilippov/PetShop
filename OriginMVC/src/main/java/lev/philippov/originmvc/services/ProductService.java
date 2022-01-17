package lev.philippov.originmvc.services;


import lev.philippov.originmvc.domain.product.structure.Product;
import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.repositories.CategoryRepository;
import lev.philippov.originmvc.repositories.ParamRepository;
import lev.philippov.originmvc.repositories.ProductRepository;
import lev.philippov.originmvc.services.mappers.CategoryMapper;
import lev.philippov.originmvc.services.mappers.ParamMapper;
import lev.philippov.originmvc.services.mappers.ProductMapper;
import lev.philippov.originmvc.web.models.CategoryDto;
import lev.philippov.originmvc.web.models.ParamDto;
import lev.philippov.originmvc.web.models.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static lev.philippov.originmvc.utils.ProductSpecificationBuilder.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ParamRepository paramRepository;
    private final ParamMapper paramMapper;

    public List<ProductDto> findAll(){
        return productRepository
                .findAll().stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }
    @Cacheable(cacheNames = {"categoryCache"})
    public List<CategoryDto> findAllCategories(){
        return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDto).collect(Collectors.toList());
    }

    public Page<ProductDto> findFiltered(Integer pageNbr, Map<String, String> params,Integer pageSize) {
        final int pageSize1 = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
        PageRequest pageRequest = PageRequest.of(pageNbr, pageSize1, Sort.by(Sort.Direction.ASC, "title"));
        Page<Product> productPage = productRepository.findAll(buildProductSpecification(params), pageRequest);
        List<ProductDto> productDtos = productPage.getContent()
                .stream().map(productMapper::productToProductDto).collect(Collectors.toList());
        return new PageImpl<ProductDto>(productDtos,pageRequest,productPage.getTotalElements());
    }

    @Transactional
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public ProductDto findById(UUID id) throws ServerException {
        if (Objects.isNull(id)) {
            throw new ServerException("Id should not be null!");
        }
        Product product = productRepository.findProductByIdWithAllDetails(id)
                .orElseThrow(() -> new ServerException("Продукт с запрошенным ID отсутсвует в базе!"));
        return productMapper.productToProductDto(product);
    }

    @Transactional
    public void save(ProductDto dto) {
        productRepository.save(productMapper.productDtoToProduct(dto));
    }

    @Transactional
    public void update(ProductDto dto) {
        if(Objects.isNull(dto.getId())){
            throw new ServerException("Id should not be null!");
        }

        if(productRepository.existsById(dto.getId())){
            productRepository.save(productMapper.productDtoToProduct(dto));
        } else {
            throw new ServerException(String.format("Impossible to update! Product with id=%s doesn't exist.", dto.getId().toString()));
        }
    }

    public List<ProductDto> findAllByIds(Collection<UUID> ids) {
        List<Product> products = productRepository.findAllById(ids);
        return products.stream().map(productMapper::productToProductDto).collect(Collectors.toList());
    }
    @Cacheable(cacheNames = {"paramsCache"})
    public List<ParamDto> findAllParams(){
        return paramRepository.findAll().stream().map(paramMapper::paramToParamDto).collect(Collectors.toList());
    }

    public ParamDto getParamById(String paramId) {
        return paramMapper.paramToParamDto(paramRepository.getById(UUID.fromString(paramId)));
    }

    public CategoryDto getCategoryById(String categoryId) {
        return categoryMapper.categoryToCategoryDto(categoryRepository.findById(UUID.fromString(categoryId)).get());
    }
}
