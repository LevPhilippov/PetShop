package lev.philippov.originmvc.web.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryDto{

    @Null
    UUID id;
    @Null
    Integer version;
    @Null
    Timestamp createdAt;
    @Null
    Timestamp updatedAt;
    private String title;
    private String description;

}
