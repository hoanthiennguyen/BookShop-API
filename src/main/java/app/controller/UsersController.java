package app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import app.message.ClickedBooksRequest;
import app.model.Book;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import app.jwt.JwtTokenProvider;
import app.message.BaseResponse;
import app.model.User;
import app.repository.UserRepository;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UsersController {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	HttpServletRequest request;
	@Autowired
	UserService userService;
	
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
	@GetMapping("/clickedBooks")
    public BaseResponse getAllClickedBooks(@AuthenticationPrincipal Authentication authenticationPrincipal){
        List<Book> books = userService.getClickedBooks(authenticationPrincipal.getName());
        BaseResponse response = new BaseResponse();
        response.setData(books);
        return response;
    }
    @PostMapping("/clickedBooks")
    public BaseResponse addBookOnListOfClickedBook(@AuthenticationPrincipal Authentication authentication,
                                                   @Valid @RequestBody ClickedBooksRequest clickedBooksRequest){
        Long bookId = clickedBooksRequest.getBookId();
        String username = authentication.getName();
       	BaseResponse response = new BaseResponse();
       	response.setData(userService.addBookToListOfClickedBooks(username,bookId));
       	return response;
    }
    @DeleteMapping("/clickedBooks/{id}")
    public BaseResponse deleteClickedBook(@AuthenticationPrincipal Authentication authentication,
										  @PathVariable long id){
		BaseResponse response = new BaseResponse();
		userService.deleteClickedBook(authentication.getName(),id);
		return response;
	}
	
	
}
