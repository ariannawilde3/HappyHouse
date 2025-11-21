package com.happyhouse.service;

import com.happyhouse.model.Post;
import com.happyhouse.repository.PostRepository;
import com.happyhouse.dto.AddPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddPostService {

    private final PostRepository postRepository;

    @Autowired
    public AddPostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

	// adds a post to the database
	// returns the objID of the post
	public String addPost(AddPostRequest post) {
		return postRepository.save(new Post(post.getTitle(), post.getContent(), post.getTags())).getObjID();
	}
}