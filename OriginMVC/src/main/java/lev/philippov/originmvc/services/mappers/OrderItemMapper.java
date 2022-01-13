package lev.philippov.originmvc.services.mappers;

import lev.philippov.originmvc.domain.OrderItem;
import lev.philippov.originmvc.web.models.OrderItemDto;
import org.mapstruct.Mapper;

@Mapper(uses = ProductMapper.class)
public interface OrderItemMapper {

    OrderItemDto orderItemToDto(OrderItem orderItem);
    OrderItem orderItemDtoToOrderItem(OrderItemDto dto);

}
