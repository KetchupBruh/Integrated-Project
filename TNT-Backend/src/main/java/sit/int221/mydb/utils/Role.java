package sit.int221.mydb.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority{ //implements GrantedAuthority//
    admin,lecturer,student;

    @Override
    public String getAuthority() {
        return name();
    }

}
