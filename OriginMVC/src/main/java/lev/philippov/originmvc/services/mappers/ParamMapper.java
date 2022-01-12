package lev.philippov.originmvc.services.mappers;

import lev.philippov.originmvc.domain.product.structure.Param;
import lev.philippov.originmvc.web.models.ParamDto;
import org.mapstruct.Mapper;

@Mapper
public interface ParamMapper {

    ParamDto paramToParamDto(Param param);

    Param paramDtoToParam(ParamDto dto);

}
