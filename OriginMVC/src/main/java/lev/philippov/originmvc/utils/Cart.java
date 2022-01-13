package lev.philippov.originmvc.utils;

import lev.philippov.originmvc.domain.OrderItem;
import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.web.models.OrderItemDto;
import lev.philippov.originmvc.web.models.ProductDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Getter
@Setter
@Data
@SessionScope
@Component
public class Cart {

    private Set<OrderItemDto> orderItems;
    private BigDecimal totalPrice;

    public Cart(){
        this.orderItems = new HashSet<>();
    }

    public void add(ProductDto product) {
        OrderItemDto orderItem = new OrderItemDto(product);
        if(!orderItems.add(orderItem)){
            orderItems.stream().filter(i->i.equals(orderItem)).forEach(i -> i.setQty(i.getQty()+1));
        }
        countPrice();
    }

    public void remove(UUID id) {
        Optional<OrderItemDto> first = orderItems.stream().filter(dto -> dto.getProduct().getId().equals(id)).findFirst();
        OrderItemDto orderItemDto = first.orElseThrow(() -> new ServerException("Error happens during removing from the cart!"));
        if(orderItemDto.getQty()>1) {
            orderItemDto.setQty(orderItemDto.getQty()-1);
        } else {
            orderItems.remove(orderItemDto);
        }
        countPrice();
    }

    public void countPrice() {
        totalPrice = new BigDecimal(0);
        for (OrderItemDto i : orderItems) {
            totalPrice = totalPrice.add(i.getProduct().getPrice().multiply(new BigDecimal(i.getQty())));
        }
    }

    public void clear() {
        orderItems.clear();
        totalPrice=new BigDecimal(0);
    }
}
