package lev.philippov.originmvc.domain.product.structure;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static org.hibernate.annotations.FetchMode.JOIN;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity{

    private String title;
    private BigDecimal price;
    private String description;
    private String upc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @Fetch(value = JOIN)
    private Category category;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "inventory_id")
    @Fetch(FetchMode.JOIN)
    private List<Inventory> inventories;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Fetch(FetchMode.JOIN)
    private List<Attribute> attributes;


}
