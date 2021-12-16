package lev.philippov.originmvc.domain.structure.components;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = "wings_components")
public class WingsComponent extends Component{

    @Builder
    public WingsComponent(String title, Param wingspan, Param liftPower) {
        super(title);
        this.wingspan = wingspan;
        this.liftPower = liftPower;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wingspan")
    private Param wingspan;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lift_power")
    private Param liftPower;

    public WingsComponent() {
        this.title = "Wings";
    }

    @Override
    public List<Param> getParams() {
        return List.of(wingspan,liftPower);
    }
}
