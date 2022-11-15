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
public class UserDetailDto {
    private Integer userId;
    private String userName;
    private String userEmail;
    private String role;
    private Instant createdOn;
    private Instant updatedOn;
    private String password;
}
