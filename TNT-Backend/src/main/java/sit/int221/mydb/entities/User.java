package sit.int221.mydb.entities;

import lombok.Getter;
import lombok.Setter;
import sit.int221.mydb.utils.Role;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Integer id;

    @Column(name = "userName", nullable = false, length = 100)
    private String userName;

    @Column(name = "userEmail", nullable = false, length = 50)
    private String userEmail;

//    @Enumerated(EnumType.STRING)
//    @Enumerated(EnumType.ORDINAL)
//    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "createdOn", nullable = false,insertable = false,updatable = false)
    private Instant createdOn;

    @Column(name = "updatedOn", nullable = false,insertable = false,updatable = false)
    private Instant updatedOn;

    @Column(name = "password", nullable = false, length = 90)
    private String password;

}