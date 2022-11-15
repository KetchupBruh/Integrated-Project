package sit.int221.mydb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int221.mydb.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public boolean existsByUserName(String userName);
    public boolean existsByUserEmail(String userEmail);
    public boolean existsByUserNameAndIdIsNot(String userName,Integer userId);
    public boolean existsByUserEmailAndIdIsNot(String userEmail,Integer userId);
    User findByUserEmail(String userEmail);
}