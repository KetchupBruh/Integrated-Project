package sit.int221.mydb.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mydb.entities.User;
import sit.int221.mydb.repositories.UserRepository;
import sit.int221.mydb.utils.Role;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("email Not found" + username);
        }

//        return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getPassword(), new ArrayList<>());

        return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getPassword(), mapRolesToAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(User user) {
//        return roles.stream()
//                .filter(Objects::nonNull)
//                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
//                .collect(Collectors.toList());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
}