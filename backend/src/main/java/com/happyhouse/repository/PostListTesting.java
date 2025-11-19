package com.happyhouse.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.happyhouse.model.Comment;
import org.springframework.stereotype.Repository;
import com.happyhouse.model.Post;

@Repository
public class PostListTesting {

    private List<Post> posts = new ArrayList<>(Arrays.asList(
		new Post(1, "I can't believe this",
			"My roof is caving in. Is this something the landlord will cover?",
			5,
			new ArrayList<>(Arrays.asList("Legal", "Safety", "Landlord")),
			new ArrayList<>(Arrays.asList(new Comment("You will get absolutely no help with this. Good luck.", "1", "one")))),
		new Post(2,
			"HELP ME!",
			"My lease sucks. I need to get out of it. Let me tell you all about it. So first, it is  super expensive. Second, the landlord is evil. Third, the location is not good.",
			200,
			new ArrayList<>(Arrays.asList("Legal", "Most Popular")),
			new ArrayList<>(Arrays.asList(new Comment("whatever", "2", "two")))),
		new Post(3, "My roommate is allergic to peanuts. Why?",
			"My roommate is allergic to peanuts and it really weirds me out. She won't ever tell me how it happened or where it started so I just give up. I'm looking for new roommates to take her place, she has two beds in her room, for some odd reason so I can definitely house more people. BLAH BLAH BALAH BALHABLHAJHDFHDOSFHDFUIWEFPBEFBEF",
			927, new ArrayList<>(0), new ArrayList<>(Arrays.asList(
			new Comment("thats rough buddy", "3", "three"),
			new Comment("im interested! heres my instagram: @ljsdhfisdgh", "4", "four"),
			new Comment("comment", "5", "five"),
			new Comment())))
	));

	
	public List<Post> getAllPosts() {
		return posts;	
	}
	
    // Retrieve posts by id
    public Post getById(int id) {
        for (int i = 0; i < 3; i++) {
			if (posts.get(i).ermid == id) {
				return posts.get(i);
			}
		}
		return new Post("not found", "not found", new ArrayList<>(0));
    }
}