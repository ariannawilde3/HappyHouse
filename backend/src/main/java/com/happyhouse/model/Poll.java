package com.happyhouse.model;
import java.util.HashSet;
import java.util.Set;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "polls")
public class Poll {
    @Id //for database
    private String id;  

    //variables
    private String title;
    private String voteOpt1;
    private String voteOpt2;
    private int totalVotes;
    private int votesFor1;
    private int votesFor2;
    private String emailOfCreator; 
    private int groupChatId; 
    private static int totalPolls; 

    //constructor
    public Poll(String title, String voteOpt1, String voteOpt2, String emailOfCreator, int groupChatId) {
        this.voteOpt1 = voteOpt1;
        this.voteOpt2 = voteOpt2;
        this.title = title;
        this.emailOfCreator = emailOfCreator;
        this.groupChatId = groupChatId;
    }

    //get and sets

    public int gettotalPolls() {
        return totalPolls;
    }

    public String getPollID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteOption1(){
        return this.voteOpt1;
    }
    public String getVoteOption2(){
        return this.voteOpt2;
    }

    public void setVoteOption1(String voteOpt1){
        this.voteOpt1 = voteOpt1;
    }

    public void setVoteOption2(String voteOpt2){
        this.voteOpt2 = voteOpt2;
    }

    //voters set used to block revotes on polls
    private Set<String> voters = new HashSet<>();

    public Set<String> getVoters() { return voters; }

    public void setVoters(Set<String> voters) { this.voters = voters; }


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

    public String getEmailOfCreator(){
        return this.emailOfCreator;
    }

    public void setEmailOfCreator(String whoCreated) {
        this.emailOfCreator = whoCreated;
    }

    public int getGCId(){
        return this.groupChatId;
    }

    public void setGCId(int id) {
        this.groupChatId=id;
    }
    
}
