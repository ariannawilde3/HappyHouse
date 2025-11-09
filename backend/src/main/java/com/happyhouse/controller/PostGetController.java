package com.happyhouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.happyhouse.model.Post;
import com.happyhouse.repository.PostListTesting;
import java.util.List;


import java.net.URI;

@RestController
@RequestMapping("/api/viewpost")
public class PostGetController {

    @Autowired
    private PostListTesting postlist;

	@GetMapping("/")
	public List<Post> getPostList() {
		return postlist.getAllPosts();
	}

    @GetMapping("/{id}")
    public Post getPost(@PathVariable int id) {
        return postlist.getById(id);
    }
}