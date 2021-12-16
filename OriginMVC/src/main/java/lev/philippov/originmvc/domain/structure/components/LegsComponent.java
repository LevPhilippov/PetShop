package lev.philippov.originmvc.domain.structure.components;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = "legs_components")
public class LegsComponent extends Component {

    @Builder
    public LegsComponent(String title, Param qtyOfLegs, Param lenghOfLegs) {
        super(title);
        this.qtyOfLegs = qtyOfLegs;
        this.lenghOfLegs = lenghOfLegs;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "qty_of_legs")
    Param qtyOfLegs;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lengh_of_legs")
    Param lenghOfLegs;

    public LegsComponent() {
        this.title = "Wings";
    }

    @Override
    public List<Param> getParams() {
        return List.of(qtyOfLegs,lenghOfLegs);
    }


}
