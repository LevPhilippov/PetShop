package lev.philippov.originmvc.web.models;

import lev.philippov.originmvc.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto implements Serializable {

    public OrderItemDto(ProductDto product){
        this.product = product;
        this.qty = 1;
    }

    private UUID id;

    private ProductDto product;

    private Integer qty;

    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return product.getId().equals(that.getProduct().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
