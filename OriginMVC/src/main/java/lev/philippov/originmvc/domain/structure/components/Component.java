package lev.philippov.originmvc.domain.structure.components;

import lev.philippov.originmvc.domain.structure.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Component extends BaseEntity {

    @Transient
    protected String title;

    public abstract List<Param> getParams();

}



