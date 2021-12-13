package lev.philippov.originmvc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1590847103740266540L;

    public Product(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<ProductImage> productImages;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
