package sit.int221.mydb.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId", nullable = false)
    private Integer id;

    @Column(name = "bookingName", nullable = false, length = 100)
    private String bookingName;

    @Column(name = "bookingEmail", nullable = false, length = 45)
    private String bookingEmail;

    @Column(name = "eventCategory", nullable = false, length = 45)
    private String eventCategory;

    @Column(name = "eventStartTime", nullable = false)
    private Instant eventStartTime;

    @Column(name = "eventDuration", nullable = false)
    private Integer eventDuration;

    @Column(name = "eventNotes", length = 500)
    private String eventNotes;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "eventCategory_eventCategoryId", nullable = false)
//    @JsonIgnoreProperties(value = {"event", "hibernateLazyInitializer"})
    private Eventcategory eventCategory_eventCategoryId;
//    private Eventcategory eventcategoryEventcategory;

//    @Column(name = "file")
//    private byte[] file;

    @Column(name = "file")
    private String file;
}
