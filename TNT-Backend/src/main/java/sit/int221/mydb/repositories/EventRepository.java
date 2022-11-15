package sit.int221.mydb.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int221.mydb.entities.Event;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "select * from event e where e.eventCategory_eventCategoryId = :eventCategory_eventCategoryId " +
            "order by e.eventStartTime desc", nativeQuery = true)
    List<Event> findAllByEventCategoryId(@Param("eventCategory_eventCategoryId")Integer eventCategory_eventCategoryId);

    @Query(value = "select * from event e where DATE_ADD(e.eventStartTime, INTERVAL e.eventDuration Minute) < :current_time " +
            "order by e.eventStartTime desc", nativeQuery = true)
    List<Event> findAllByEventPast(@Param("current_time")LocalDateTime current_time);

    @Query(value = "select * from event e where DATE_ADD(e.eventStartTime, INTERVAL e.eventDuration Minute) > :current_time " +
            "order by e.eventStartTime desc", nativeQuery = true)
    List<Event> findAllByEventFuture(@Param("current_time")LocalDateTime current_time);

    @Query(value = "select * from event e where DATE(e.eventStartTime) = :eventStartTime " +
            "order by e.eventStartTime desc", nativeQuery = true)
    List<Event> findAllByEventSelectDate(@Param("eventStartTime")Date eventStartTime);

    @Query(value = "select * from event e where e.bookingEmail = :bookingEmail order by e.eventStartTime desc", nativeQuery = true)
    List<Event> findAllByBookingEmail(@Param("bookingEmail") String bookingEmail);

    @Query(value = "select * from event e where DATE_ADD(e.eventStartTime, INTERVAL e.eventDuration Minute) < :current_time " +
            "and e.bookingEmail = :bookingEmail order by e.eventStartTime desc", nativeQuery = true)
    List<Event> findAllByEventStudentPast(@Param("bookingEmail") String bookingEmail, @Param("current_time")LocalDateTime current_time);

    @Query(value = "select * from event e where DATE_ADD(e.eventStartTime, INTERVAL e.eventDuration Minute) > :current_time " +
            "and e.bookingEmail = :bookingEmail order by e.eventStartTime desc", nativeQuery = true)
    List<Event> findAllByEventStudentPastFuture(@Param("bookingEmail") String bookingEmail, @Param("current_time")LocalDateTime current_time);

    @Query(value = "select * from event e where DATE(e.eventStartTime) = :eventStartTime " +
            "and e.bookingEmail = :bookingEmail order by e.eventStartTime desc", nativeQuery = true)
    List<Event> findAllByEventStudentSelectDate(@Param("bookingEmail") String bookingEmail, @Param("eventStartTime")Date eventStartTime);

    @Query(value = "select e.* from `event` e join `eventCategory` ec on e.eventCategory_eventCategoryId = ec.eventCategoryId" +
            " join `eventCategoryOwner` eco on ec.eventCategoryId = eco.eventCategoryId" +
            " join `user` u on eco.userId = u.userId where u.userId = :userId", nativeQuery = true)
    List<Event> findAllByEventCategoryOwner(@Param("userId") int userId);

    @Query(value = "select e.* from `event` e join `eventCategory` ec on e.eventCategory_eventCategoryId = ec.eventCategoryId" +
            " join `eventCategoryOwner` eco on ec.eventCategoryId = eco.eventCategoryId" +
            " join `user` u on eco.userId = u.userId where e.eventId = :eventId and u.userId = :userId", nativeQuery = true)
    Optional<Event> findEventByEventCategoryOwner(@Param("eventId") int eventId,@Param("userId") int userId);

}
