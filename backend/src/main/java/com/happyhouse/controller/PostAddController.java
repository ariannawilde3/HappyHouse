package com.happyhouse.controller;

//TODO: addpost returns response entity
//rk.http.ResponseEntity

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.happyhouse.repository.PostRepository;
import com.happyhouse.dto.AddPostRequest;

@RestController
@RequestMapping("/api/addpost")
public class PostAddController {
	
	private final PostRepository postrepo;
	@Autowired
	public PostAddController(PostRepository postrepo) {
		this.postrepo = postrepo;
	}
	
	@PostMapping("/")
	public String addPost(@RequestBody AddPostRequest post) {
		return "{\"id\" : \"" + postrepo.save(post.getPost()).objID + "\"}";
	}
}