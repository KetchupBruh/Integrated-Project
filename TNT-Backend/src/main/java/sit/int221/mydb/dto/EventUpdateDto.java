package sit.int221.mydb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateDto {
    private Integer eventId;

    @Future(message = "Pls enter date time future")
    private Instant eventStartTime;

    @Size(max = 500,message = "Your Note can't over 500 words")
    private String eventNotes;

    private MultipartFile file;
}
