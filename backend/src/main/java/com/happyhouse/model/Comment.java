package com.happyhouse.model;

public class Comment {
	public int id;
	public int votes;
	public String username;
	public String content;
	
	
	// for testing only
	public Comment () {
		this.id = 0;
		this.votes = 3;
		this.username = "testuser";
		this.content = "test comment";
	}
	
	// for testing only
	public Comment (int id, String content, int votes) {
		this.id = id;
		this.votes = votes;
		this.content = content;
	}
	
	public Comment (int id, String content) {
		this.id = id;
		this.votes = 0;
		this.content = content;
	}
}