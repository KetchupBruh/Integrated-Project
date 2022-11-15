package sit.int221.mydb.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventAllDto {
    private Instant eventStartTime;
    private Integer eventDuration;
    private String eventCategoryName;
    private String bookingName;
    private Integer eventId;
}
