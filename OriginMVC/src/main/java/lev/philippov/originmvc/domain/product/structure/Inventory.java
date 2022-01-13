package lev.philippov.originmvc.domain.product.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "inventories")
public class Inventory extends BaseEntity{

    @Positive
    Integer qty;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Override
    public String toString() {
        return "Inventory{" +
                "qty=" + qty +
                '}';
    }
}
