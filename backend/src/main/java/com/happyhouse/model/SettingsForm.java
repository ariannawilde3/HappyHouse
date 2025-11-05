package com.happyhouse.model;

public class SettingsForm {
    private int roomieCount;
    private boolean anonNamesOn;
    private boolean regenNamesOn;
    private int inviteCode;

    // constructor

    public SettingsForm(int roomieCount, boolean anonNamesOn, boolean regenNamesOn, int inviteCode) {
        this.roomieCount = roomieCount;
        this.anonNamesOn = anonNamesOn;
        this.regenNamesOn = regenNamesOn;
        this.inviteCode = inviteCode;
    }

    //get and sets

    public int getRoomieCount() {
        return roomieCount;
    }

    public void setRoomieCount(int roomieCount) {
        this.roomieCount = roomieCount;
    }

    public boolean isAnonNamesOn() {
        return anonNamesOn;
    }

    public void setAnonNames(boolean anonNamesOn) {
        this.anonNamesOn = anonNamesOn;
    }

    public boolean isRegenNamesOn() {
        return regenNamesOn;
    }

    public void setRegenNames(boolean regenNamesOn) {
        this.regenNamesOn = regenNamesOn;
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }
}
