package app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.jwt.JwtTokenProvider;
import app.message.BaseResponse;
import app.model.User;
import app.repository.UserRepository;


@RestController
@RequestMapping("/api/user")
public class UsersController {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired 
	JwtTokenProvider jwt;
	
	private String getToken() {
		String [] header = request.getHeader("Authorization").split(" ");
		String token = header[header.length-1];
		String username = jwt.getUserNameFromJwt(token);
		return username;
	}
	
	@GetMapping()
	public BaseResponse getUserProfile() {
		String username = getToken();
		return new BaseResponse(200, "User Profile", userRepo.findByUser(username));
	}
	
	@PutMapping()
	public BaseResponse updateProfile(@AuthenticationPrincipal Authentication auth,@RequestBody User user) {
		User result = userRepo.findByUser(auth.getName());
		
		if(result!=null) {
			
			result.setEmail(user.getEmail());
			result.setAddress(user.getAddress());
			result.setFullname(user.getFullname());
			result.setPhone(user.getPhone());
			userRepo.save(result);
			return new BaseResponse(200,"Update Done",result);
		}
		return new BaseResponse(500, "Update Failed", user);
	}
	
	@DeleteMapping("/{id}")
	public BaseResponse deleteUser(@PathVariable Long id) {
		userRepo.deleteById(id);
		return new BaseResponse(200, "Delete",id);
	}
	
	
}
