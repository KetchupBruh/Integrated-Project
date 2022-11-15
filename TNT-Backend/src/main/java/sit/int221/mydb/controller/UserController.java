package sit.int221.mydb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import sit.int221.mydb.dto.*;
import sit.int221.mydb.entities.Eventcategory;
import sit.int221.mydb.entities.User;
import sit.int221.mydb.services.UserService;
import sit.int221.mydb.utils.Role;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    @PreAuthorize("hasRole('admin')")
    public List<UserAllDto> getUserAll() {
        return userService.getUserAll();
    }

    @GetMapping("{userId}")
    public UserDetailDto getUserDetail(@PathVariable Integer userId){
        return userService.getUserDetail(userId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserAddDto newUser) {
        return userService.createUser(newUser);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Integer userId){
        userService.deleteUser(userId);
    }

    @PutMapping("{userId}")
    public User edit(@Valid @RequestBody UserEditDto editUser ,@PathVariable Integer userId) {
        return userService.editUser(editUser,userId);
    }

}
