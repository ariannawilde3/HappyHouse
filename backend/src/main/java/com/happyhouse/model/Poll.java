package com.happyhouse.model;

public class Poll {
    private int pollID;
    private String title;
    private String[] voteOptions;
    private int timeUntilTimeout;
    private int totalVotes;
    private int votesFor1;
    private int votesFor2;
    private User whoCreated;

    private static int totalPolls; //change when database added

    //constructor
    public Poll(String title, String voteOpt1, String voteOpt2, String[] voteOptions, int lengthRequested, User whoCreated) {
        this.voteOptions = voteOptions;
        this.pollID = gettotalPolls() + 1;
        this.title = title;
        this.timeUntilTimeout = lengthRequested;
        this.whoCreated = whoCreated;
    }

    //get and sets

    public int gettotalPolls() {
        return totalPolls;
    }

    public int getPollID() {
        return pollID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteOption1(){
        return voteOptions[0];
    }
    public String getVoteOption2(){
        return voteOptions[1];
    }

    public void setVoteOptions(String voteOption1, String voteOption2) {
        this.voteOptions[0] = voteOption1;
        this.voteOptions[1] = voteOption2;
    }

    public int getTimeUntilTimeout(){
        return timeUntilTimeout;
    }

    public void setTimeUntilTimeout(int timeUntilTimeout) {
        this.timeUntilTimeout = timeUntilTimeout;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void addToTotalVotes() {
        this.totalVotes += 1;
    }
    public int getVotesFor1() {
        return votesFor1;
    }

    public void addToVotesFor1() {
        this.votesFor1 += 1;
    }

    public int getVotesFor2() {
        return votesFor2;
    }

    public void addToVotesFor2() {
        this.votesFor2 += 1;
    }

    public User getWhoCreated(){
        return whoCreated;
    }

    public void setWhoCreated(User whoCreated) {
        this.whoCreated = whoCreated;
    }
}
