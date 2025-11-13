package com.happyhouse.model;
import java.security.SecureRandom;
// settings for private group chats
public class SettingsForm {
    private int roomieCount;
    private String housename;
    private int inviteCode = 0;

    private SecureRandom random = new SecureRandom();

    // constructor

    public SettingsForm(int roomieCount, String housename) {
        this.roomieCount = roomieCount;
        this.housename = housename;
        this.inviteCode = createInviteCode();
        if (inviteCode == -1) {
            throw new IllegalArgumentException("createInviteCode alreadyCreated boolean not reset");
        }
    }

    //get and sets

    public int getRoomieCount() {
        return roomieCount;
    }

    public void setRoomieCount(int roomieCount) {
        this.roomieCount = roomieCount;
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public int createInviteCode() { /*random number gen for gc code */
        boolean alreadyCreated = false;
        int code = -1;
        while (!alreadyCreated) {
            code = 100000 + random.nextInt(900000);
            /*TODO: check database to see if in there */
            alreadyCreated = true;
        }
        return code;
    }

    public void setHouseName(String housename) {
        this.housename = housename;
    }

    public String getHouseName() {
        return this.housename;
    }

    @Override
    public String toString() {
        return "SettingsForm{" +
                "houseName='" + housename + '\'' +
                ", roomieCount=" + roomieCount +
                '}';
    }

}
