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

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, mappedBy = "product")
    @Fetch(JOIN)
    private List<Inventory> inventories;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "product",fetch = FetchType.LAZY)
    private List<Attribute> attributes;
}
