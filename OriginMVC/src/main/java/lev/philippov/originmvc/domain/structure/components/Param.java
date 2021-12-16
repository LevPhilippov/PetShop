package lev.philippov.originmvc.domain.structure.components;

import lev.philippov.originmvc.domain.structure.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "params")
@NoArgsConstructor
public class Param extends BaseEntity {

    @Builder
    public Param(String name, String value, TypeOfMeasure typeOfMeasure) {
        this.name = name;
        this.value = value;
        this.typeOfMeasure = typeOfMeasure;
    }
    //this parameter shouldn't be a simple String - it has to be a mature Entity and have its own table with all the parameters may appear.
    //it would be handy to add a new params into the database and this practice will reduce the number of mistakes during adding new param values.
    String name;

    String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_measure")
    TypeOfMeasure typeOfMeasure;
}
