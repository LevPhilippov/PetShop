package lev.philippov.originmvc.services.mappers;

import lev.philippov.originmvc.domain.product.structure.Attribute;
import lev.philippov.originmvc.web.models.AttributeDto;
import org.mapstruct.Mapper;

@Mapper(uses = {ParamMapper.class})
public interface AttributeMapper {

    AttributeDto attributeToAttributeDto(Attribute attribute);

    Attribute attributeDtoToAttribute(AttributeDto dto);

}
