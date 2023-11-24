package com.registration.demo;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody UserRegisterationInfo newUserInfo) {
		
		return new ResponseEntity<UserRegisterationInfo>(userService.save(newUserInfo), HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/users")
	public ResponseEntity<List<UserRegisterationInfo>> getAllUsers(){
		List<UserRegisterationInfo> usersInfos = userService.getAllUsersInfos();
		return new ResponseEntity<>(usersInfos, HttpStatus.OK);
	}
	

    @CrossOrigin
    @GetMapping("/checkemail")
    public ResponseEntity<String> checkUserInfo(@RequestParam String email) {
        String result = userService.checkUserInfo(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
	
	 @CrossOrigin
	    @PostMapping("/login")
	    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) throws UserPrincipalNotFoundException {
	        try {
	            String result = userService.authenticateUser(email, password);
	            return new ResponseEntity<>(result, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
	 }
	
	

}
