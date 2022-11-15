package sit.int221.mydb.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sit.int221.mydb.Security.JwtUtil;
import sit.int221.mydb.dto.AuthenticationResponse;
import sit.int221.mydb.dto.LoginDto;
import sit.int221.mydb.services.MyUserDetailsService;
import sit.int221.mydb.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @PostMapping("")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUserEmail(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserEmail());
//
//        final String jwt = jwtUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
        return userService.login(loginRequest);
    }

    @GetMapping("refreshToken")
    public ResponseEntity<AuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        return userService.refreshAndGetAuthenticationToken(request);
    }
}
