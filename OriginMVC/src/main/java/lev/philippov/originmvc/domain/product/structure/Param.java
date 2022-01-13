package lev.philippov.originmvc.domain.product.structure;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "params")
public class Param extends BaseEntity{

    private String title;
    private String measure;

}
