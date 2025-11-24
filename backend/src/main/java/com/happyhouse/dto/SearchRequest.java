package com.happyhouse.dto;

import java.util.List;
import java.util.ArrayList;

public class SearchRequest {
	private String title;
	private List<String> tags;
	
	public SearchRequest() {
		this.title = "";
		this.tags = new ArrayList<>(0);
	}
	
	public SearchRequest(String title, List<String> tags) {
		this.title = title;
		this.tags = tags;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<String> getTags() {
		return tags;
	}
}