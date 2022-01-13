package lev.philippov.originmvc.web.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProductDto{



    @Null
    UUID id;
    @Null
    Integer version;
    @Null
    Timestamp createdAt;
    @Null
    Timestamp updatedAt;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    private String description;
    private String upc;
    private String category;
    private Integer inventory;
    private List<AttributeDto> attributes = new ArrayList<>();



}
