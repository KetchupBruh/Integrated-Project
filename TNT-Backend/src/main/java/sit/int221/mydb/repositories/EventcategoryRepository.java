package sit.int221.mydb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int221.mydb.entities.Event;
import sit.int221.mydb.entities.Eventcategory;

import java.util.List;

public interface EventcategoryRepository extends JpaRepository<Eventcategory, Integer> {
    @Query(value = "select count(*) from eventCategory ec where ec.eventCategoryId != :eventCategoryId and ec.eventCategoryName = :eventCategoryName"
            , nativeQuery = true)
    Integer findCheckUniqueName(@Param("eventCategoryId")Integer eventCategoryId, @Param("eventCategoryName")String eventCategoryName);
}