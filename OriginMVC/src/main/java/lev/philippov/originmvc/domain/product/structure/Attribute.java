package lev.philippov.originmvc.domain.product.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        return "Attribute{" +
                "value='" + value + '\'' +
                ", param=" + param +
                '}';
    }
}
