package lev.philippov.originmvc.domain.structure.components;

import lev.philippov.originmvc.domain.structure.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Inventory extends BaseEntity {

    @Builder
    public Inventory(Integer qty) {
        this.qty = qty;
    }

    Integer qty;
}
