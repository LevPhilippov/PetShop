package lev.philippov.originmvc.domain.structure.components;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = "body_components")
public class BodyComponent extends Component {

    @Builder
    public BodyComponent(String title, Param weight) {
        super(title);
        this.weight = weight;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "weight")
    Param weight;

    public BodyComponent() {
        this.title = "Body";
    }

    @Override
    public List<Param> getParams() {
        return Arrays.stream(this.getClass().getDeclaredFields()).map(f-> {
            try {
                return (Param)f.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }


}
