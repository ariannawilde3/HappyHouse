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
	
	// returns 1 if ok
	// multiplies by 2 if title is too short
	// multiplies by 3 if content is too short
	// multiplies by 5 if title is too long
	// multiplies by 7 if content is too long
	public int checkValidInputs(AddPostRequest post) {
		return 1 * (post.getTitle().length() > 0 ? 1 : 2) * 
					(post.getContent().length() > 0 ? 1 : 3) *
					(post.getTitle().length() <= 50 ? 1 : 5) *
					(post.getContent().length() <= 1200 ? 1 : 7);
	}
}