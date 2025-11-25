package junit;

import com.happyhouse.model.Post;
import com.happyhouse.repository.PostRepository;
import com.happyhouse.service.GetPostService;
import org.junit.Test;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestGetPost {
	@Test
	public void testGetPostWithTags() {
        PostRepository postRepository = mock(PostRepository.class);
		GetPostService testGetPostService = new GetPostService(postRepository);

		
        Post testPost = new Post("test post", "content here", new ArrayList<>(Arrays.asList("Safety")));

		when(postRepository.findById(testPost.getObjID())).thenReturn(Optional.of(testPost));
        
		Post output = testGetPostService.getPost(testPost.getObjID());
		
		assertEquals("test post", output.getTitle());
        assertEquals("content here", output.getContent());
        assertEquals(Arrays.asList("Safety"), output.getTags());
	}

    @Test
    public void testGetPostNoTags() {
        PostRepository postRepository = mock(PostRepository.class);
        GetPostService testGetPostService = new GetPostService(postRepository);


        Post testPost = new Post("Post title!", "c", new ArrayList<>());

        when(postRepository.findById(testPost.getObjID())).thenReturn(Optional.of(testPost));

        Post output = testGetPostService.getPost(testPost.getObjID());

        assertEquals("Post title!", output.getTitle());
        assertEquals("c", output.getContent());
        assertEquals(new ArrayList<>(), output.getTags());
    }

}