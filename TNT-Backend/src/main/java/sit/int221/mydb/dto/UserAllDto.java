package sit.int221.mydb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.mydb.utils.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAllDto {
    private Integer userId;
    private String userName;
    private String userEmail;

    @Enumerated(EnumType.ORDINAL)
    private Role role;
}
