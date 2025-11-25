package com.happyhouse.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "posts")
public class Post {
	@Id
	private String objID;
	private String content;
	@Indexed
	private String title;
	private int votes;
	private List<String> upvotedBy = new ArrayList<>();
    private List<String> downvotedBy = new ArrayList<>();


	private List<String> tags;
	private List<Comment> comments = new ArrayList<>();
		
	
	// only for testing
	public Post(String title, String content, int votes, List<String> tagList, List<Comment> comments) {
		this.content = content;
		this.title = title;
		this.votes = votes;
		this.tags = tagList;
		this.comments = comments;
	}
	
	public Post(String title, String content, List<String> tagList) {
		this.content = content;
		this.title = title;
		this.votes = 0;
		this.tags = tagList;
		this.comments = new ArrayList<>(4);
	}
	
	public Post() {
		this.content = "HELP!";
		this.title = "i really can't";
		this.votes = 0;
		this.tags = null;
		this.comments = null;
	}
	
	public String getObjID() {
		return this.objID;
	}

    public String getContent() {
        return content;
    }
	
	public void setContent(String content) {
        this.content = content;
    }
	
	public String getTitle() {
		return title;
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
	
	public List<String> getTags() {
		return tags;
	}

    // vote getter and setters

	public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

	public List<String> getUpvotedBy() {
        return upvotedBy;
    }
    
    public void setUpvotedBy(List<String> upvotedBy) {
        this.upvotedBy = upvotedBy;
    }
    
    public List<String> getDownvotedBy() {
        return downvotedBy;
    }
    
    public void setDownvotedBy(List<String> downvotedBy) {
        this.downvotedBy = downvotedBy;
    }
    
    public void upvote(String userId) {
        // Remove if previously downvoted
        if (downvotedBy.contains(userId)) {
            downvotedBy.remove(userId);
            votes++;
        }
        
        // upvote if not already upvoted
        if (!upvotedBy.contains(userId)) {
            upvotedBy.add(userId);
            votes++;
        }
    }
    
    public void downvote(String userId) {
        // Remove if previously upvoted
        if (upvotedBy.contains(userId)) {
            upvotedBy.remove(userId);
            votes--;
        }
        
        // downvote if not already downvoted
        if (!downvotedBy.contains(userId)) {
            downvotedBy.add(userId);
            votes--;
        }
    }

    public String getUserVote(String userId) {
        if (upvotedBy.contains(userId)) return "up";
        if (downvotedBy.contains(userId)) return "down";
        return null;
    }

	@Override
	public String toString() {
		return "post " + this.title + this.content;
	}
}