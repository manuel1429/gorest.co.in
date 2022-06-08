package com.jmrt.gorest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jmrt.gorest.services.UserService;
import com.jmrt.gorest.models.UserModel;


@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	public UserModel[] getAllUsers(@RequestParam(required = false) String name){
		if(name!=null) {
			return userService.getUserByName(name);
		}
		return userService.getAllUsers();
	}
	
	@GetMapping("/users/{user_ref}")
	public UserModel getUserById(@PathVariable Long user_ref){
		return userService.getUserById(user_ref);
	}
	
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public UserModel createNewUser(@RequestHeader("Authorization") String authToken, @RequestBody UserModel user){
		return userService.createNewUser(authToken, user);
	}
	
	@PutMapping("/users/{user_ref}")
	public UserModel updateUser(@PathVariable Long user_ref,@RequestHeader("Authorization") String authToken, @RequestBody UserModel user) {
		return userService.updateUser(user_ref, authToken, user);
	}
	
	@PatchMapping("/users/{user_ref}")
	public UserModel partialUpdateUser(@PathVariable Long user_ref,@RequestHeader("Authorization") String authToken, @RequestBody String user) {
		return userService.partialUpdateUser(user_ref, authToken, user);
	}
	
	@DeleteMapping("/users/{user_ref}")
	public ResponseEntity<?> deleteUser(@PathVariable Long user_ref,@RequestHeader("Authorization") String authToken) {
		Boolean response = userService.deleteUser(user_ref, authToken);
		if(response) {
	        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	    } else {
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
