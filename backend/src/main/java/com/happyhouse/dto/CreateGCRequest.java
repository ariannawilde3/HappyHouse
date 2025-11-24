package com.happyhouse.dto;

public class CreateGCRequest {
    private String houseName;
    private int expectedRoomieCount;

    public String getHouseName() { 
        return houseName; 
    }

    public void setHouseName(String houseName) { 
        this.houseName = houseName; 
    }

    public int getExpectedRoomieCount() { 
        return expectedRoomieCount; 
    }

    public void setExpectedRoomieCount(int expectedRoomieCount) { 
        this.expectedRoomieCount = expectedRoomieCount; 
    }
}