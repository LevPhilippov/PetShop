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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Inventory> inventories;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, mappedBy = "product")
    private List<Attribute> attributes;
}
