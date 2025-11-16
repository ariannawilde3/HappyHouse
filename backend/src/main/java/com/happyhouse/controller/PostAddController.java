package com.happyhouse.controller;

//TODO: addpost returns response entity

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.happyhouse.model.Post;
import com.happyhouse.repository.PostListTesting;
import com.happyhouse.repository.PostRepository;
import com.happyhouse.dto.AddPostRequest;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


import java.net.URI;

@RestController
@RequestMapping("/api/addpost")
public class PostAddController {
	
	@Autowired
	private PostRepository postrepo;
	
	@PostMapping("/")
	public String addPost(@RequestBody AddPostRequest post) {
		return "{\"id\" : \"" + postrepo.save(post.getPost()).objID + "\"}";
	}
}