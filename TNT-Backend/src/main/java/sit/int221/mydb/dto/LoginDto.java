package sit.int221.mydb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email isn't correct form",regexp = ".+@.+\\.[a-z]+[a-z]+")
    @Size(max=50,message = "Email is over 50 words")
    private String userEmail;

    @NotBlank(message = "Password is mandatory")
    @Size(max=14,message = "Password is over 14 words")
    @Size(min=8,message = "Password is less 8 words")
    private String password;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }
}
