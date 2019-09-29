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

import app.jwt.JwtTokenProvider;
import app.message.BaseResponse;
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
    public BaseResponse registerUser(@Valid @RequestBody SignUpRequest signup){
    	User result = userRepository.checkDup(signup.getUsername(), signup.getEmail());
    	BaseResponse res = new BaseResponse();
    	if(result == null) {
    		User user = new User(signup.getUsername(), signup.getEmail(), encoder.encode(signup.getPassword()));
    		userRepository.save(user);
    		res.setCode(200);
    		res.setMessage("Sign up successful!!!");
    		res.setData(result);
    		return res;
    	}
    	return new BaseResponse(500,"Duplicat username or email",signup);
    }
  

}
