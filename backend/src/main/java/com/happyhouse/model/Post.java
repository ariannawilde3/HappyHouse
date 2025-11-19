package com.happyhouse.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "posts")
public class Post {
	@Id
	public String objID;
	//public int ermid;
	public String content;
	@Indexed
	public String title;
	public int votes;
	public List<String> tags;
	public List<Comment> comments = new ArrayList<>();
		
	
	// only for testing
	public Post(int id, String title, String content, int votes, List<String> tagList, List<Comment> comments) {
		//this.ermid = id;
		this.content = content;
		this.title = title;
		this.votes = votes;
		this.tags = tagList;
		this.comments = comments;
	}
	
	public Post(String title, String content, List<String> tagList) {
		//this.ermid = -1;
		this.content = content;
		this.title = title;
		this.votes = 0;
		this.tags = tagList;
		this.comments = new ArrayList<>(4);
	}
	
	public Post() {
		//this.ermid = 0;
		this.content = "HELP!";
		this.title = "i really can't";
		this.votes = 0;
		this.tags = null;
		this.comments = null;
	}
	
	public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }
    
    public void removeComment(Comment comment) {
        if (comments != null) {
            comments.remove(comment);
        }
    }
    
    public List<Comment> getComments() {
        return comments;
    }

	@Override
	public String toString() {
		return "post " + this.title + this.content;
	}
}