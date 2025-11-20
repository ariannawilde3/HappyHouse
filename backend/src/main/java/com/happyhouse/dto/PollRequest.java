package com.happyhouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PollRequest {
    private String title;

    @JsonProperty("voteOption1")
    private String voteOption1;

    @JsonProperty("voteOption2")
    private String voteOption2;

    public String getTitle() { return title; }
    public String getVoteOption1() { return voteOption1; }
    public String getVoteOption2() { return voteOption2; }
}