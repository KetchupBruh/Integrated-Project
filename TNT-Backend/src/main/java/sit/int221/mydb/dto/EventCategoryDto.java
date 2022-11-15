package sit.int221.mydb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCategoryDto {
    private Integer id;

    @NotBlank(message = "Category Name is mandatory")
    @Size(max = 100, message = "Your Category Name can't over 100 words")
    private String eventCategoryName;

    @Size(max = 500, message = "Your Note can't over 500 words")
    private String eventCategoryDescription;

    @NotNull
    @Min(1)
    @Max(480)
    private Integer eventDuration;
}
