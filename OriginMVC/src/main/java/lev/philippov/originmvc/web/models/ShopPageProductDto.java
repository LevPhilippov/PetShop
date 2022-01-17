package lev.philippov.originmvc.web.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ShopPageProductDto {
    private UUID id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    private String description;
    private String upc;
    private Integer inventory;
}
