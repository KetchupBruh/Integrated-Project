package sit.int221.mydb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import sit.int221.mydb.entities.Eventcategory;

import javax.validation.constraints.*;
import java.sql.Date;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventScheduleDto {
    private Integer eventId;

    @NotNull
    @Future(message = "Pls enter date time future")
    private Instant eventStartTime;

    private Integer eventDuration;

    @NotBlank(message = "Name is mandatory")
    @Size(max=100,message = "Your name is over 100 words")
    private String bookingName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email isn't correct form",regexp = ".+@.+\\.[a-z]+[a-z]+")
    @Size(max=45,message = "Email is over 45 words")
    private String bookingEmail;

    @Size(max = 500,message = "Your Note can't over 500 words")
    private String eventNotes;

    @NotNull
    private Integer eventCategory_eventCategoryId;

    private MultipartFile file;

}
