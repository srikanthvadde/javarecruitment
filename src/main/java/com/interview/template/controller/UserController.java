package com.interview.template.controller;

import java.util.List;

import com.interview.template.exceptions.UserNotFoundException;
import com.interview.template.model.UserEntity;
import com.interview.template.service.UserService;

import lombok.AllArgsConstructor;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(UserController.BASE_URL)
@AllArgsConstructor
class UserController {

	static final String BASE_URL = "/api/v1/users";

	private final UserService userService;

	@GetMapping
	public List<UserEntity> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{userId}")
	UserEntity getUser(@PathVariable Long userId) throws UserNotFoundException {
		return userService.getUser(userId);
	}

	@RequestMapping(method = RequestMethod.HEAD, value = "/{userId}")
	void checkExists(@PathVariable Long userId) throws UserNotFoundException {
		userService.checkUserExists(userId);
	}
	
	@PostMapping("/createuser")
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) throws Exception {
		
		UserEntity userEntityNew = userService.createUser(userEntity);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userEntityNew.getId()).toUri();
		return ResponseEntity.created(location).build();

	}
	
	@DeleteMapping("/deleteuser/{id}")
	public void deleteStudent(@PathVariable long id) throws UserNotFoundException {
		userService.deleteById(id);
	}
	
	
	//we can use getAllUsers() also by passing a parameter but for code reusabilty and better readibility i have written seperate method
	@GetMapping("/getmatchuserlist}")
	public List<UserEntity> getMatchedUserList(@RequestParam String enterUserName) throws UserNotFoundException {
		return userService.getMatchedUserList(enterUserName);
	}
	
}
