package ooc.squishtable.main.controller;

import ooc.squishtable.main.jwt.JwtUtils;
import ooc.squishtable.main.model.AppJwt;
import ooc.squishtable.main.model.AppText;
import ooc.squishtable.main.model.AppUser;
import ooc.squishtable.main.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AdminService adminService;

    /**
     * Sing in
     * @param loginRequest
     * @return token
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AppUser loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User loggingInUser = (User) authentication.getPrincipal();
        AppUser findingUser = adminService.getCurrentInfo(loggingInUser.getUsername());
        List<String> roles = adminService.getRoles(findingUser);

        return ResponseEntity.ok(new AppJwt(jwt,
                findingUser.getId(),
                findingUser.getUsername(),
                roles));
    }

    /**
     * Register
     * @param signUpRequest
     * @return Message to tell that user register successfully
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AppUser signUpRequest) {

        adminService.addNewUser(signUpRequest);

        return ResponseEntity.ok(new AppText("User registered successfully!"));
    }
}
