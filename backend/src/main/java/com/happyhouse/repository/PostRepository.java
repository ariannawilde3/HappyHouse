package com.happyhouse.repository;

import com.happyhouse.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
// Repositories allow the backend to interact with the database
// this connects to our mongo db and allows us to use the stored data and filter it
public interface PostRepository extends MongoRepository<Post, String> {
    
    Optional<Post> findById(String id);

    Optional<Post> findByObjID(String objID);
    
    boolean existsByObjID(String objID);
	
	@Query(value = "{'title': {$regex : /^?0/, $options: 'i'}}")
	List<Post> findByTitleStartingWith(String title, Pageable pageable);
	
	@Query(value = "{'title': {$regex : /^?0/, $options: 'i'}, 'tags': {$all: ?1}}")
	List<Post> findByTags(String title, List<String> tags, Pageable pageable);

    @Query("{ 'comments.id': ?0 }")
    Optional<Post> findByCommentId(String commentId);
}
