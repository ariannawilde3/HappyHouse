package com.happyhouse.dto;

import java.util.List;
import com.happyhouse.model.Post;

// look into jakarta to validate? @Size?
public class AddPostRequest {
    
    private String title;
    private String content;
	private List<String> tags;
    
    // Constructors
    public AddPostRequest() {
		this.title = "blank title";
		this.content = "blank content";
		this.tags = null;
    }
    
    public AddPostRequest(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
		this.tags = tags;
    }
	
	public Post getPost() {
		return new Post(title, content, tags);
	}
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
	
	public List<String> getTags() {
		return tags;
	}
}