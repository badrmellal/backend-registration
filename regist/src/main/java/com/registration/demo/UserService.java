package com.registration.demo;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	
	public UserRegisterationInfo save(UserRegisterationInfo newUserInfo) {
		 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = bCryptPasswordEncoder.encode(newUserInfo.getPassword());
		newUserInfo.setPassword(encodedPassword);
		
		return userRepository.save(newUserInfo);
		
	}
	
	public String checkUserInfo(String email) {
	    Optional<UserRegisterationInfo> userEmailInfo = userRepository.findByEmail(email);

	    if (userEmailInfo.isPresent()) {
	        return "User with " + email + " already exists in the database.";
	        
	    } else {
	        return "User with " + email + " doesn't exist in the database.";
	        
	    }
	}

	
	 public String authenticateUser(String email, String enteredPassword) throws UserPrincipalNotFoundException, Exception {
	        Optional<UserRegisterationInfo> userOptional = userRepository.findByEmail(email);

	        if (userOptional.isPresent()) {
	            UserRegisterationInfo dbUserInfo = userOptional.get();

	            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	            if (passwordEncoder.matches(enteredPassword, dbUserInfo.getPassword())) {
	                return "Authentication successful!";
	            } else {
	                throw new Exception("Incorrect password");
	            }
	            
	        } else {
	            throw new UserPrincipalNotFoundException("User not found");
	        }
	    }

	 
	
	public List<UserRegisterationInfo> getAllUsersInfos(){
		return userRepository.findAll();
	}

	
}
