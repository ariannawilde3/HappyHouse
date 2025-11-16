package com.happyhouse.controller;

//TODO: post list just returns previews

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
@RequestMapping("/api/viewpost")
public class PostGetController {

    @Autowired
    private PostListTesting postlist;
	
	@Autowired
	private PostRepository postrepo;

	@GetMapping("/")
	public List<Post> getPostList() {
		//return postlist.getAllPosts();
		return postrepo.findAll();
	}

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id) {
        return postrepo.findById(id)
		.orElseThrow(() -> new RuntimeException("Post not found"));
    }
	
	@GetMapping("/add")
	public Post addPost() {
		Post newPost = new Post(
		"test content",
		"test title",
		new ArrayList(Arrays.asList("test tag", "two tag")));
		
		postrepo.save(newPost);
		return newPost;
	}
}