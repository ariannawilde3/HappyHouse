package com.happyhouse.model;

import com.happyhouse.model.Comment;
import java.util.ArrayList;
import java.util.List;

public class Post {
	public int id;
	public String content;
	public String title;
	public int votes;
	public List<String> tags;
	public List<Comment> comments;
	
	
	// only for testing
	public Post(int id, String title, String content, int votes, List<String> tagList, List<Comment> comments) {
		this.id = id;
		this.content = content;
		this.title = title;
		this.votes = votes;
		this.tags = tagList;
		this.comments = comments;
	}
	
	public Post(int id, String title, String content, List<String> tagList) {
		this.id = id;
		this.content = content;
		this.title = title;
		this.votes = 0;
		this.tags = tagList;
		this.comments = new ArrayList<Comment>(4);
	}
	
	@Override
	public String toString() {
		return "post " + this.title + this.content;
	}
}