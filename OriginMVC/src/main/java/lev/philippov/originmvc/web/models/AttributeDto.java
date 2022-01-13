package lev.philippov.originmvc.web.models;

import lev.philippov.originmvc.domain.product.structure.Param;
import lombok.*;

import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AttributeDto{

    @Null
    UUID id;
    @Null
    Integer version;
    @Null
    Timestamp createdAt;
    @Null
    Timestamp updatedAt;

    private String value;

    private ParamDto param;
}
