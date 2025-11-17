package com.happyhouse.repository;

import com.happyhouse.model.GCInfo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface AllGroupChats extends MongoRepository<GCInfo, String> {

    Boolean existsByInviteCode(int inviteCode);

    Optional<GCInfo> findByInviteCode(int inviteCode);
}