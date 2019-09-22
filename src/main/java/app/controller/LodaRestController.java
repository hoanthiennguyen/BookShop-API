package app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.message.JwtResponse;
import app.message.LoginRequest;
import app.message.LoginResponse;
import app.message.SignUpForm;
import app.model.User;
import app.model.repository.UserRepository;
import app.model.security.jwt.JwtTokenProvider;
import app.model.security.services.CustomUserDetails;




@RestController
@RequestMapping("/api")
public class LodaRestController {
	
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

        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

       
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody  SignUpForm signup){
    	
    	System.out.println(signup.getPassword());
    	User user = new User(signup.getUsername(), signup.getEmail(), encoder.encode(signup.getPassword()));
    	userRepository.save(user);
    	return ResponseEntity.ok().body("User registered successfull");
    }
  

}
