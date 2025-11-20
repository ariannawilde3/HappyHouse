package com.happyhouse.repository;

import com.happyhouse.model.Poll;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PollsRepository extends MongoRepository<Poll, String> {

    Boolean existsByPollID(int pollID);

    Optional<Poll> findByPollID(int pollID);

    List<Poll> findByGroupChatId(int groupChatId);
}