package lev.philippov.originmvc.services.mappers;

import lev.philippov.originmvc.domain.product.structure.Category;
import lev.philippov.originmvc.web.models.CategoryDto;
import org.mapstruct.Mapper;


@Mapper
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto(Category category);
    Category categoryDtoToCategory(CategoryDto dto);
}
