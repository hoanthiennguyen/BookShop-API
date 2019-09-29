package app.service;

 
import javax.transaction.Transactional;

import app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.model.User;
import app.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        
        User user = userRepository.findByUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }
    
    
    public User updateProfile(User user, String username) {
    	User result = userRepository.findByUsername(username);
    	if(result!=null) {
    		user.setUsername(username);
    		userRepository.save(user);
    	}
    	return null;
    	
    }
    
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);
    }



}