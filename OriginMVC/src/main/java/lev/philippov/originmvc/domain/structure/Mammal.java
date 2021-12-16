package lev.philippov.originmvc.domain.structure;

import lev.philippov.originmvc.domain.structure.components.BodyComponent;
import lev.philippov.originmvc.domain.structure.components.Component;
import lev.philippov.originmvc.domain.structure.components.Inventory;
import lev.philippov.originmvc.domain.structure.components.LegsComponent;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mammals")
public class Mammal extends Animal{

    @Builder
    public Mammal(String title, BigDecimal price, Inventory qty, LegsComponent legs) {
        super(title, price, qty, AnimalType.MAMMAL);
        this.legs = legs;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "legs_id")
    LegsComponent legs;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "body_id")
    BodyComponent body;

    @Override
    List<Component> getComponents() {
        return List.of(legs,body);
    }
}
