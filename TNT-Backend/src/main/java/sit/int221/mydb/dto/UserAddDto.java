package sit.int221.mydb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.mydb.Exceptions.StringEnumeration;
import sit.int221.mydb.utils.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
public class UserAddDto {
    private Integer userId;

    @NotBlank(message = "Name is mandatory")
    @Size(max=100,message = "Name is over 100 words")
    private String userName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email isn't correct form",regexp = ".+@.+\\.[a-z]+[a-z]+")
    @Size(max=50,message = "Email is over 50 words")
    private String userEmail;

//    @Enumerated(EnumType.ORDINAL)
//    @Enumerated(EnumType.STRING)
    @StringEnumeration(enumClass=Role.class)
    private String role;

    @NotBlank(message = "Password is mandatory")
    @Size(max=14,message = "Password is over 14 words")
    @Size(min=8,message = "Password is less 8 words")
    private String password;
    private Instant createdOn;
    private Instant updatedOn;

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail.trim();
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }
}
