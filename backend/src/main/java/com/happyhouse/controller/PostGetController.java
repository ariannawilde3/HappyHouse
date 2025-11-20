package com.happyhouse.controller;

//TODO: post list just returns previews

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.happyhouse.model.Post;
import com.happyhouse.repository.PostListTesting;
import com.happyhouse.repository.PostRepository;
import org.springframework.data.domain.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api/viewpost")
public class PostGetController {

    @Autowired
    private PostListTesting postlist;
	
	@Autowired
	private PostRepository postrepo;

	@GetMapping("/all/{page}")
	public List<Post> getPostList(@PathVariable int page) {
		Pageable pageable = PageRequest.of(page, 3, Sort.by("_id").descending());
		return postrepo.findAll(pageable).getContent();
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
		new ArrayList<String>(Arrays.asList("test tag", "two tag")));
		
		postrepo.save(newPost);
		return newPost;
	}
}