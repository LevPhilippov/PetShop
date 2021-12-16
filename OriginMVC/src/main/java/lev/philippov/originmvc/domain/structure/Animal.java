package lev.philippov.originmvc.domain.structure;


import lev.philippov.originmvc.domain.structure.components.Component;
import lev.philippov.originmvc.domain.structure.components.Inventory;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@MappedSuperclass
@Getter
@Setter
public abstract class Animal extends BaseEntity {

    public Animal(String title, BigDecimal price, Inventory qty, AnimalType type) {
        this.title = title;
        this.price = price;
        this.qty = qty;
        this.type = type;
    }

    String title;
    BigDecimal price;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "qty")
    Inventory qty;

    @Enumerated(EnumType.STRING)
            @Column(name = "animal_type")
    AnimalType type;

    abstract List<Component> getComponents();

}
