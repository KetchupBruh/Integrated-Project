package sit.int221.mydb.entities;

import javax.persistence.*;

@Entity
@Table(name = "eventCategoryOwner")
public class Eventcategoryowner {
    @EmbeddedId
    private EventcategoryownerId id;

    @MapsId("eventCategoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "eventCategoryId", nullable = false)
    private Eventcategory eventCategory;

    @MapsId("userid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User userid;

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public Eventcategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(Eventcategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public EventcategoryownerId getId() {
        return id;
    }

    public void setId(EventcategoryownerId id) {
        this.id = id;
    }
}