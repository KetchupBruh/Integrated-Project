package sit.int221.mydb.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mydb.Security.JwtUtil;
import sit.int221.mydb.dto.*;
import sit.int221.mydb.entities.Event;
import sit.int221.mydb.entities.Eventcategory;
import sit.int221.mydb.entities.User;
import sit.int221.mydb.repositories.UserRepository;
import sit.int221.mydb.utils.ListMapper;
import sit.int221.mydb.utils.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    public List<UserAllDto> getUserAll() {
        List<User> userAllList = userRepository.findAll(Sort.by("userName").ascending());
        return listMapper.mapList(userAllList, UserAllDto.class, modelMapper);
    }

    public UserDetailDto getUserDetail(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does't exist !!"));
        return modelMapper.map(user, UserDetailDto.class);
    }

    public User createUser(UserAddDto addUser) {
        if(addUser.getRole() == null){
            addUser.setRole(String.valueOf(Role.student));
        }
        addUser.setUserName(addUser.getUserName().trim());
//        addUser.setPassword(passwordService.securePassword(addUser.getPassword()));
        addUser.setPassword(passwordService.securePassword(addUser.getPassword()));
        User user = modelMapper.map(addUser, User.class);
        if(userRepository.existsByUserName(addUser.getUserName()) && userRepository.existsByUserEmail(addUser.getUserEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Have exist Name and E-mail");
        }
        if(userRepository.existsByUserName(addUser.getUserName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Have exist Name");
        }
        if(userRepository.existsByUserEmail(addUser.getUserEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Have exist E-mail");
        }
        User saveUser = userRepository.saveAndFlush(user);
        saveUser.setPassword("******");
        return saveUser;

//        return userRepository.saveAndFlush(user);
    }

    //password check
//    public User passwordCheck(LoginDto login){
//        User user = userRepository.findByUserEmail(login.getUserEmail());
//        if(user == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Don't have this email");
//        }
//        if(login.getUserEmail().equals(user.getUserEmail())){
////            if(passwordService.validatePassword(login.getPassword(),user.getPassword())){
//            if(passwordService.validatePassword(user.getPassword(),login.getPassword())){
//                throw new ResponseStatusException(HttpStatus.OK,"Password is valid");
//            }
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is NOT valid");
//        }
//        return null;
//    }

    //password check + jwt
    public ResponseEntity<?> login(LoginDto login){
        User user = userRepository.findByUserEmail(login.getUserEmail());
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Don't have this email");
        }
        if(login.getUserEmail().equals(user.getUserEmail())){
            if(passwordService.validatePassword(user.getPassword(),login.getPassword())){
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                login.getUserEmail(),
                                login.getPassword()
                        )
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUserEmail());
                final String accessToken = jwtUtil.generateToken(userDetails); //30 min
                final String refreshToken = jwtUtil.refreshToken(accessToken); //refresh in 24 hr.

                return ResponseEntity.ok(new JwtDto("Login successful",accessToken,refreshToken));
            }else{
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is NOT valid");
            }
        }
        return null;
    }

    public User editUser(UserEditDto editUser,Integer userId) {
        editUser.setUserName(editUser.getUserName().trim());
        User user = userRepository.findById(userId).orElseThrow(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        modelMapper.map(editUser,user);
        User u = modelMapper.map(user,User.class);
        if(userRepository.existsByUserNameAndIdIsNot(editUser.getUserName(),userId) && userRepository.existsByUserEmailAndIdIsNot(editUser.getUserEmail(),userId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Have exist Name and E-mail");
        }
        if(userRepository.existsByUserNameAndIdIsNot(editUser.getUserName(),userId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Have exist Name");
        }
        if(userRepository.existsByUserEmailAndIdIsNot(editUser.getUserEmail(),userId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Have exist E-mail");
        }
        return userRepository.saveAndFlush(u);
    }

    public void deleteUser(@PathVariable Integer userId){
        userRepository.findById(userId).orElseThrow(()->new RuntimeException(userId+ " does not exist !!!"));
        userRepository.deleteById(userId);
    }

    public ResponseEntity<AuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        final String token = authToken.substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        UserDetails userDetail = userDetailsService.loadUserByUsername(username);

        if (jwtUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtUtil.generateToken(userDetail);
            return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
