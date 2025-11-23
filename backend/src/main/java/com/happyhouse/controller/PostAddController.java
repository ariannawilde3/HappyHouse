package com.happyhouse.controller;

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
		int status = addPostService.checkValidInputs(post);
		if (status != 1) {
			return "{\"status\" : " + status + "}";
		}
		
		return "{\"status\" : 1, \"id\" : \"" + addPostService.addPost(post) + "\"}";
	}
}