package com.happyhouse.controller;

//TODO: addpost returns response entity
//rk.http.ResponseEntity

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.happyhouse.service.AddPostService;
import com.happyhouse.dto.AddPostRequest;

@RestController
@RequestMapping("/api/addpost")
public class PostAddController {
	
	private final AddPostService addPostService;
	@Autowired
	public PostAddController(AddPostService addPostService) {
		this.addPostService = addPostService;
	}
	
	// adds the post to the database
	// returns the objID of the post in JSON format to the front end
	@PostMapping("/")
	public String addPost(@RequestBody AddPostRequest post) {
		return "{\"id\" : \"" + addPostService.addPost(post) + "\"}";
	}
}