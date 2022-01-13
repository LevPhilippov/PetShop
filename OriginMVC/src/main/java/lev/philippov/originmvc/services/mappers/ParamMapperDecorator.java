package lev.philippov.originmvc.services.mappers;

import lev.philippov.originmvc.domain.product.structure.Param;
import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.repositories.ParamRepository;
import lev.philippov.originmvc.web.models.ParamDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ParamMapperDecorator implements ParamMapper{

    private ParamRepository paramRepository;

    @Autowired
    public void setParamRepository(ParamRepository paramRepository) {
        this.paramRepository = paramRepository;
    }

    @Override
    public ParamDto paramToParamDto(Param param) {
        ParamDto dto = new ParamDto();
        dto.setId(param.getId());
        dto.setTitle(param.getTitle());
        dto.setMeasure(param.getMeasure());
        return dto;
    }

    @Override
    public Param paramDtoToParam(ParamDto dto) {
        Optional<Param> byId = paramRepository.findById(dto.getId());
        return byId.orElseThrow(()-> new ServerException("Error during params mapping!"));
    }
}
