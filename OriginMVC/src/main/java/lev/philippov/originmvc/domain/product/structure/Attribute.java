package lev.philippov.originmvc.domain.product.structure;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "attributes")
public class Attribute extends BaseEntity {

    private String value;

    @OneToOne
    @JoinColumn(name = "param_id")
    private Param param;
}
