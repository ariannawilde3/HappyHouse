package com.happyhouse.model;

public class GCLock {
    private int associatedGCCode;
    private boolean unlocked;
    private int expectedRoomieCount;
    private int currentRoomieCount;

    //constructor
    public GCLock(int associatedGCCode, int expectedRoomieCount) {
        this.associatedGCCode = associatedGCCode;
        this.expectedRoomieCount = expectedRoomieCount;
        this.unlocked = false;
        this.currentRoomieCount = 1;
    }

    // get and sets

    public int getAssociatedGCCode() {
        return associatedGCCode;
    }

    public void setAssociatedGCCode(int associatedGCCode) {
        this.associatedGCCode = associatedGCCode;
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
