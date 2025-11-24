package com.happyhouse.service;

import com.happyhouse.model.Post;
import com.happyhouse.repository.PostRepository;
import com.happyhouse.repository.UserRepository;
import com.happyhouse.dto.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;


import java.util.List;

@Service
public class GetPostService {
	public static final String POST404 = "Post not found";
	
    private final PostRepository postrepo;
	@Autowired
	public GetPostService(PostRepository postrepo) {
		this.postrepo = postrepo;
	}

	public List<Post> getPostList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("_id").descending());
		return postrepo.findAll(pageable).getContent();
	}
	
	public Post getPost(String id) {
        return postrepo.findById(id)
		.orElseThrow(() -> new RuntimeException(POST404));
    }
	
	public List<Post> getPostSearch(SearchRequest search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("_id").descending());
		
		if (search.getTags().isEmpty()) {
			return postrepo.findByTitleStartingWith(search.getTitle(), pageable);
		}
		return postrepo.findByTags(search.getTitle(), search.getTags(), pageable);
	}
}