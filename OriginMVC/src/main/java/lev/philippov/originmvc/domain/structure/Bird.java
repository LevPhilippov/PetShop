package lev.philippov.originmvc.domain.structure;

import lev.philippov.originmvc.domain.structure.components.*;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "birds")
public class Bird extends Animal{

    @Builder
    public Bird(String title, BigDecimal price, Inventory qty, LegsComponent legs, BodyComponent body, WingsComponent wings) {
        super(title, price, qty, AnimalType.BIRD);
        this.legs = legs;
        this.body = body;
        this.wings = wings;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "legs_id")
    LegsComponent legs;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "body_id")
    BodyComponent body;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wings_id")
    WingsComponent wings;

    @Override
    List<Component> getComponents() {
        return List.of(legs,body,wings);
    }
}



