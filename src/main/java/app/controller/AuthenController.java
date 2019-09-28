package app.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import app.jwt.JwtTokenProvider;
import app.message.JwtResponse;
import app.message.LoginRequest;
import app.message.SignUpRequest;
import app.model.User;
import app.repository.UserRepository;
import app.service.CustomUserDetails;




@RestController
@RequestMapping("/api/authen")
public class AuthenController {
	
	@Autowired
	UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody  LoginRequest loginRequest) {

        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

       
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest signup){
    	
    	System.out.println(signup.getPassword());
    	User user = new User(signup.getUsername(), signup.getEmail(), encoder.encode(signup.getPassword()));
    	userRepository.save(user);
    	return ResponseEntity.ok().body("User registered successfully");
    }
    @GetMapping("/checkUsername")
    public boolean checkUsernameExist(@RequestParam String username){
        //TO-DO
        return false;
    }
    @GetMapping("/checkEmail")
    public boolean checkEmailExist(@RequestParam String email){
        //TO-DO
        return false;
    }
  

}
