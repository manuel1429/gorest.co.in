package com.jmrt.gorest.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.jmrt.gorest.models.UserModel;

@Service
public class UserService {
	
	@Autowired
	private RestTemplate restTemplate;
	private String url;
	
	
	public UserModel[] getAllUsers(){
		url = "https://gorest.co.in/public/v2/users";
		ResponseEntity<UserModel[]> response =  restTemplate.getForEntity(url, UserModel[].class);
		UserModel[] users = response.getBody();
		return users;
	}
	
	public UserModel getUserById(Long user_ref){
		url = "https://gorest.co.in/public/v2/users/{ID}";
		ResponseEntity<UserModel> response =  restTemplate.getForEntity(url, UserModel.class, user_ref);
		UserModel user = response.getBody();
		return user;
	}
	
	public UserModel[] getUserByName(String name){
		url = "https://gorest.co.in/public/v2/users?name={name}";
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		
		ResponseEntity<UserModel[]> response =  restTemplate.exchange(url, HttpMethod.GET, entity, UserModel[].class, params);
		UserModel[] user = response.getBody();
		return user;
	}
	
	public UserModel createNewUser(String authToken, UserModel user) {
		url = "https://gorest.co.in/public/v2/users";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", authToken);
		HttpEntity<?> entity = new HttpEntity<>(user,headers);
		
		ResponseEntity<UserModel> response =  restTemplate.exchange(url, HttpMethod.POST, entity, UserModel.class);
		user = response.getBody();
		return user;
	}

	public UserModel updateUser(Long user_ref, String authToken, UserModel user) {
		url = "https://gorest.co.in/public/v2/users/{ID}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", authToken);
		HttpEntity<?> entity = new HttpEntity<>(user,headers);
		
		ResponseEntity<UserModel> response =  restTemplate.exchange(url, HttpMethod.PUT, entity, UserModel.class, user_ref);
		user = response.getBody();
		return user;
	}

	public UserModel partialUpdateUser(Long user_ref, String authToken, String body) {
		url = "https://gorest.co.in/public/v2/users/{ID}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", authToken);
		HttpEntity<?> entity = new HttpEntity<>(body,headers);
		
		ResponseEntity<UserModel> response =  restTemplate.exchange(url, HttpMethod.PATCH, entity, UserModel.class, user_ref);
		UserModel user = response.getBody();
		return user;
	}

	public Boolean deleteUser(Long user_ref, String authToken) {
		try {
			url = "https://gorest.co.in/public/v2/users/{ID}";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", authToken);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			
			restTemplate.exchange(url, HttpMethod.DELETE, entity, UserModel.class, user_ref);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
