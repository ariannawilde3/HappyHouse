package com.happyhouse.model;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

//all info for gc, including house name, current and expected counts, lock, and codes DOES NOT INCLUDE MEMBERS
@Document (collection = "GCInformation")
public class GCInfo {

    @Id //lets database give id to obj
    private String id; 

    //private variables
    private String housename;
    @Indexed (unique = true) private int inviteCode = 0; //indexed and unique for databse ease
    private boolean unlocked;
    private int expectedRoomieCount;
    private int currentRoomieCount;


    public GCInfo() { //empty constructor for calling 
    }
   
    //constructor
    public GCInfo (int expRoomieCount, String housename) {
        this.expectedRoomieCount = expRoomieCount;
        this.housename = housename;
        createInviteCode();
        this.unlocked = false;
        this.currentRoomieCount = 1;

    }

    //get and sets
    public int getInviteCode() {
        return inviteCode;
    }

    public int createInviteCode() { //random code gen for codes
        int code = -1;
        code = 100000 + ThreadLocalRandom.current().nextInt(900000);
        this.inviteCode = code;
        return code;
    }

    public void setInviteCode(int code) {
        this.inviteCode = code;
    }

    public void setHouseName(String housename) {
        this.housename = housename;
    }

    public String getHouseName() {
        return this.housename;
    }

    @Override
    public String toString() { //debugging
        return "SettingsForm{" +
                "houseName='" + housename + '\'' +
                ", roomieCount=" + expectedRoomieCount +
                '}';
    }

    public String getId() {
        return this.id;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean getUnlocked() {
        return unlocked;
    }

    public int getCurrentRoomieCount() {
        return currentRoomieCount;
    }

    public void addToCurrentRoomieCount() {
        this.currentRoomieCount += 1;
    } //add rather than set, since should only increase when roommate has joined

    public void setExpectedRoomieCount(int expectedRoomieCount) {
        this.expectedRoomieCount = expectedRoomieCount;
    }

    public int getExpectedRoomieCount() {
        return expectedRoomieCount;
    }
}