package com.happyhouse.repository;

import com.happyhouse.model.GCInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllGroupChats extends MongoRepository<GCInfo, String> {
    
    //is there a code?
    Boolean existsByInviteCode(int inviteCode);

    //find the gc info, if code exists
    Optional<GCInfo> findByInviteCode(int inviteCode);

}