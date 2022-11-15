package sit.int221.mydb.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventcategoryownerId implements Serializable {
    private static final long serialVersionUID = -4076333725846055069L;
    @Column(name = "eventCategoryId", nullable = false)
    private Integer eventCategoryId;
    @Column(name = "userid", nullable = false)
    private Integer userid;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getEventCategoryId() {
        return eventCategoryId;
    }

    public void setEventCategoryId(Integer eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, eventCategoryId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventcategoryownerId entity = (EventcategoryownerId) o;
        return Objects.equals(this.userid, entity.userid) &&
                Objects.equals(this.eventCategoryId, entity.eventCategoryId);
    }
}