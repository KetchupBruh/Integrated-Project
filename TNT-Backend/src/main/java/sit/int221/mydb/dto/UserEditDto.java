package sit.int221.mydb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.mydb.Exceptions.StringEnumeration;
import sit.int221.mydb.utils.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {
    private Integer userId;
    @Size(max=100,message = "Name is over 100 words")
    private String userName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email isn't correct form",regexp = ".+@.+\\.[a-z]+[a-z]+")
    @Size(max=50,message = "Email is over 50 words")
    private String userEmail;

//    @Enumerated(EnumType.STRING)
    @StringEnumeration(enumClass= Role.class)
    private String role;
}
