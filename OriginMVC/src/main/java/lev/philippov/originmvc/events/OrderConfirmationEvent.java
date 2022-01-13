package lev.philippov.originmvc.events;

import lev.philippov.originmvc.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderConfirmationEvent {
    private Long orderId;
    private OrderStatus orderStatus;
}
