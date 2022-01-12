package lev.philippov.originmvc.domain;

import lev.philippov.originmvc.utils.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private BigDecimal price;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "details_id")
    OrderDetails orderDetails;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "order")
    Set<OrderItem> orderItems;

    public Order(List<OrderItem> orderItemList, BigDecimal price, String userId, OrderDetails orderDetails) {
        this.orderItems = new HashSet<>();
        this.orderItems.addAll(orderItemList);
        this.price = price;
        this.userId = userId;
        this.orderDetails = orderDetails;
        this.orderStatus = OrderStatus.NOT_CONFIRMED;

        for (OrderItem o : this.orderItems) {
            o.setOrder(this);
        }
    }
    public Order(List<OrderItem> orderItems, BigDecimal price, OrderDetails orderDetails) {
        this.orderItems = new HashSet<>();
        this.orderItems.addAll(orderItems);
        this.price = price;
        this.orderDetails = orderDetails;
        this.orderStatus = OrderStatus.NOT_CONFIRMED;

        for (OrderItem o : this.orderItems) {
            o.setOrder(this);
        }
    }

    @Version
    Integer version;

    @CreationTimestamp
    @Column(name = "created_at")
    OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    OffsetDateTime updatedAt;
}
